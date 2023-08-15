package com.bagamao.interfaceservice.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bagamao.apicommon.entity.InterfaceInfo;
import com.bagamao.apicommon.entity.User;
import com.bagamao.apicommon.exception.BusinessException;
import com.bagamao.apicommon.service.AccountService;
import com.bagamao.apicommon.service.InterfaceInfoService;
import com.bagamao.apicommon.service.UserService;
import com.bagamao.apicommon.util.JsonResult;
import com.bagamao.interfaceservice.mapper.InterfaceInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
@Slf4j
public class InterfacerServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    @Autowired
    private InterfaceInfoMapper interfaceInfoMapper;

    @DubboReference
    private UserService userService;

    @DubboReference
    private AccountService accountService;

    @Override
    public InterfaceInfo getInterfaceInfo(Long interfaceId, String urlPath, String method, String params) {
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        if(interfaceId != null){
            wrapper.eq("id", interfaceId);
        }
//        wrapper.eq("url_path", urlPath).eq("method", method).eq("request_params", params);
        return interfaceInfoMapper.selectOne(wrapper);
    }

    @Override
    public String invokeApiWeb(Long userId, Long InterfaceInfoId, String params) {
        return null;
    }

    @Override
    public String invokeApiSDK(String accessKey, String url, String params) {
        return null;
    }

    @Override
    public String invokeApi() {
        Long interfaceInfoId = 1L;
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectById(interfaceInfoId);
        User invokeUser = userService.getInvokeUser("bagamao");
        // 调用一下
        log.info("接口调用人: " + invokeUser.toString());
        log.info("接口价格 " + interfaceInfo.getPrice());
        // 扣费
        try {
            Integer res = accountService.decreaseAccount(invokeUser.getId(), interfaceInfo.getPrice());
        }catch (Exception e){
            throw new BusinessException(JsonResult.SYS_ERROR, "扣费失败");
        }

        // 生成一条记录
        return null;
    }
}
