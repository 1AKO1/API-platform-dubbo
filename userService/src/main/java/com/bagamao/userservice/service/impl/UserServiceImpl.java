package com.bagamao.userservice.service.impl;

import com.bagamao.apicommon.entity.User;
import com.bagamao.apicommon.service.UserService;
import com.bagamao.userservice.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DubboService
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        log.info("接受参数:" + accessKey);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("access_key", accessKey);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.selectList(null);
    }
}
