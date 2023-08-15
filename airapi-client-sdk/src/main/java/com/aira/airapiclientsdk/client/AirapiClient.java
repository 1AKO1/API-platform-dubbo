package com.aira.airapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.aira.airapiclientsdk.model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.aira.airapiclientsdk.utils.SignUtils.getSign;


public class AirapiClient {
    private static final String GATEWAY_HOST = "http://localhost:8100";
    private String accessKey;
    private String secretKey;

    public AirapiClient() {
    }

    public AirapiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result= HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        // 一定不要发送给后端
//        map.put("secretKey", secretKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("body", URLEncoder.encode(body, "UTF-8"));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("sign", getSign(body, secretKey));
        return map;
    }

    public String getUserNameByPost(User user){
        String jsonStr = JSONUtil.toJsonStr(user);
        try (HttpResponse response = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(jsonStr))
                .body(jsonStr)
                .execute()) {
            System.out.println(response.getStatus());
            String result = response.body();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}
