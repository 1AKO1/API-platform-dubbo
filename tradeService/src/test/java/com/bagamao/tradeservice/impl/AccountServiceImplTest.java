package com.bagamao.tradeservice.impl;

import com.bagamao.apicommon.service.AccountService;
import com.bagamao.tradeservice.TradeServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradeServiceApplication.class)
public class AccountServiceImplTest {
    @Resource
    private AccountService accountService;


    @Test
    public void testAddAccount(){
        Integer integer = accountService.addAccount(2L, 20L);
        System.out.println(integer);
    }

}