package com.bagamao.apicommon.service;

/**
* @author 14184
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-08-08 15:00:26
*/
public interface InnerUserInterfaceInfoService{

    /*
    * 调用次数统计
    * */
    boolean invokeCount(Long interfaceInfo, Long userId);
}
