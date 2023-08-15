package com.bagamao.apicommon.service;

import com.bagamao.apicommon.entity.InterfaceInfo;

/**
* @author 14184
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-08-02 23:12:09
*/
public interface InterfaceInfoService {
    /*
     * 从数据库中查询接口是否存在
     * */
    InterfaceInfo getInterfaceInfo(Long interfaceId, String urlPath, String method, String params);

    /**
     * 网页：传输用户名，接口id
     * sdk：传输accessKey，接口url
     */
    String invokeApiWeb(Long userId, Long InterfaceInfoId, String params);
    String invokeApiSDK(String accessKey, String url, String params);
    String invokeApi();

}
