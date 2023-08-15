package com.aira.airapigateway;

import com.aira.airapiclientsdk.utils.SignUtils;
import com.bagamao.apicommon.entity.InterfaceInfo;
import com.bagamao.apicommon.entity.User;
import com.bagamao.apicommon.service.InterfaceInfoService;
import com.bagamao.apicommon.service.InnerUserInterfaceInfoService;
import com.bagamao.apicommon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
 * 全局过滤
 * // TODO 流量染色
 * */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private static final Set<String> IP_WHERE_SET = new HashSet<>(Arrays.asList("127.0.0.1"));
    private static final String INTERFACE_HOST = "http://localhost:8123";

    @DubboReference
    private UserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InterfaceInfoService innerInterfaceInfoService;

    // 1. 用户发送请求到api网关
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 2. 请求日志，不可缺少！！！！
        ServerHttpRequest request = exchange.getRequest();
        String url = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + url);
        log.info("请求方法：" + method);
        log.info("请求参数：" + queryParams);
        String suorceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();
        // 3. 黑白名单
        if(!IP_WHERE_SET.contains(suorceAddress)){
            // 可以封装一个全局异常对象，直接抛异
            return handleNoAuth(response);
        }
        // 4. 判断ak sk是否合法，如果合法则放行，否则拦截
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = null;
        try {
            body = URLDecoder.decode(headers.getFirst("body"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        final Long FIVE_MINUTES = 60 * 5L;
        if(Long.parseLong(nonce) > 10000 || (System.currentTimeMillis() / 1000 - Long.parseLong(timestamp)) > FIVE_MINUTES){
            return handleNoAuth(response);
        }
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.error("getInvokeUser error, " + e);
        }
        if(invokeUser == null){
            return handleNoAuth(response);
        }
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body, secretKey);
        if(sign == null || !sign.equals(serverSign)){
            return handleNoAuth(response);
        }
        // 5. 判断接口是否存在，状态是否正常，看哪个SQL需求小，先做轻逻辑
        InterfaceInfo interfaceInfo = null;
        try{
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(url, method, queryParams.toString());
        }catch (Exception e){
            log.error("getInterface error, " + e);
        }
        if(interfaceInfo == null){
            return handleNoAuth(response);
        }
        // TODO 校验用户是否还有调用次数
        // 6. 请求转发，调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);
//        log.info("响应：" + response.getStatusCode());
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());

    }

    /*
    * 处理响应
    * */
    // TODO: 这个位置增加了两个参数是对原方法的侵入，本身只是为了处理Response，却要因为增加一个invoke方法多传递两个参数，这是值得的吗
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Long interfaceInfoId, Long userId) {
        try {
            // 原始 response 对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if(statusCode == HttpStatus.OK){

                // 装饰器 增强原始response 对象的能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据，拼接拼接字符串
                            Mono<Void> voidMono = super.writeWith(
                                fluxBody.map(dataBuffer -> {
                                    // 7. TODO 调用成功，调用次数 + 1 invokeCount
                                    Boolean invokeResult = false;
                                    try {
                                        invokeResult = innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                    }catch (Exception e){
                                        // TODO: 可加告警系统
                                        log.error("invokeCount error," + e);
                                    }
                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(content);
                                    DataBufferUtils.release(dataBuffer);//释放掉内存
                                    // 构建日志
                                    StringBuilder sb2 = new StringBuilder(200);
                                    sb2.append("<--- {} {} \n");
                                    List<Object> rspArgs = new ArrayList<>();
                                    rspArgs.add(originalResponse.getStatusCode());

                                    //rspArgs.add(requestUrl);
                                    String data = new String(content, StandardCharsets.UTF_8);//data
                                    sb2.append(data);
                                    log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                    return bufferFactory.wrap(content);
                                }));
                            return voidMono;
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置response对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
