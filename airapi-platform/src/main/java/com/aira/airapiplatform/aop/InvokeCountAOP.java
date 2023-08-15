package com.aira.airapiplatform.aop;

import com.aira.airapiplatform.controller.NameController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

@RestControllerAdvice
public class InvokeCountAOP {
//    @Resource
//    private UserInterfaceInfoService userInterfaceInfoService;

    // 定义切面触发时机（什么时候执行方法） controller代码执行成功后，执行
//    public void doInvokeCount(Joint){
        // 调用方法
//        object.proceed();
        //调用成功，次数 + 1;
//        userInterfaceInfoService.invokeCount();
//    }
}
