package com.bagamao.userservice.controller;


import com.bagamao.apicommon.entity.User;
import com.bagamao.apicommon.service.UserService;
import com.bagamao.apicommon.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getInvokeUser/{accessKey}")
    public JsonResult<User> getInvokeUser(@PathVariable("accessKey") String accessKey){
        return JsonResult.ok(userService.getInvokeUser(accessKey));
    }

    @GetMapping("/getlist")
    public JsonResult<List<User>> getUserList(){
        return JsonResult.ok(userService.getUserList());
    }
}
