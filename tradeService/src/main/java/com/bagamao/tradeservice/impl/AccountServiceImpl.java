package com.bagamao.tradeservice.impl;

import com.bagamao.apicommon.entity.Account;
import com.bagamao.apicommon.exception.BusinessException;
import com.bagamao.apicommon.service.AccountService;
import com.bagamao.apicommon.util.JsonResult;
import com.bagamao.tradeservice.mapper.AccountMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@DubboService
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Integer addAccount(Long userId, Long changeNum) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Long count = accountMapper.selectCount(wrapper);
        if(count == 0){
            Account account = new Account();
            account.setUserId(userId);
            account.setAccount(changeNum);
            return accountMapper.insert(account);
        }else{
            Map<String, Long> map = new HashMap<>();
            map.put("userId", userId);
            map.put("changeNum", changeNum);
            return accountMapper.updateAccountByUserId(map);
        }
    }

    @Override
    public Integer decreaseAccount(Long userId, Long changeNum) {
        log.info("接受参数:" + userId + ", " + changeNum);
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Long count = accountMapper.selectCount(wrapper);
        if(count == 0){
            throw new BusinessException(JsonResult.PARAMS_ERROR, "用户不存在");
        }else{
            Map<String, Long> map = new HashMap<>();
            map.put("userId", userId);
            map.put("changeNum", changeNum);
            return accountMapper.decreaseAccountByUserId(map);
        }
    }
}
