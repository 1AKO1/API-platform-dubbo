package com.aira.airapiplatform.controller;

import cn.hutool.json.JSONUtil;
import com.aira.airapiclientsdk.model.User;
import com.aira.airapiclientsdk.utils.SignUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/*
* 名称API
* */
@RestController // 这里传字符串是给bean取名字
@RequestMapping("/name")
public class NameController {
    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request){
        System.out.println(request.getHeader("yupi"));
//        throw new RuntimeException("我日");
        return "Get 你的名字是：" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name, HttpServletRequest request){
        return "Post 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) throws UnsupportedEncodingException {
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = URLDecoder.decode(request.getHeader("body"), "UTF-8");

        // TODO 校验随机数
        if(Long.parseLong(nonce) > 10000){
            throw new RuntimeException("无权限");
        }
        // TODO 校验时间戳
//        if(timestamp){
//
//        }
        // TODO 实际情况是从数据库中查询到SecretKey，然后使用
//        String serverSign = SignUtils.getSign(body, "bagamao");
//        String wuhuSign = SignUtils.getSign(JSONUtil.toJsonStr(user), "bagamao");

        // TODO:应该去库里查 判断该用户状态是否正常，是否存在这个key
//        if(!serverSign.equals(sign)){
//            throw new RuntimeException("用户无权限");
//        }
        String result = "restPost 你的名字是：" + user.getUserName();
        // 调用成功后，次数 + 1

        return result;
    }
}
