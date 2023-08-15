package com.bagamao.interfaceservice.controller;


import com.bagamao.apicommon.entity.InterfaceInfo;
import com.bagamao.apicommon.entity.User;
import com.bagamao.apicommon.service.InterfaceInfoService;
import com.bagamao.apicommon.service.UserService;
import com.bagamao.apicommon.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interface")
public class InterfaceController {
    @Autowired
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/get/detail/{id}")
    private JsonResult<InterfaceInfo> getInterfaceDetail(@PathVariable("id") Long interfaceInfoId){
        return JsonResult.ok(interfaceInfoService.getInterfaceInfo(interfaceInfoId, null, null, null));
    }

    @GetMapping("/invoke")
    private JsonResult invokeAPI(){
        return JsonResult.ok(interfaceInfoService.invokeApi());
    }

}
