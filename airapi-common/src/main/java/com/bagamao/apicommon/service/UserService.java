package com.bagamao.apicommon.service;

import com.bagamao.apicommon.entity.User;

import java.util.List;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface UserService {

    /*
     * 数据库中查找是否给用户密钥
     * */
    User getInvokeUser(String accessKey);

    List<User> getUserList();

}
