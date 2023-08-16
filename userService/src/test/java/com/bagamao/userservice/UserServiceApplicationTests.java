package com.bagamao.userservice;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.Acceleration;
import com.bagamao.apicommon.service.AccountService;
import com.bagamao.apicommon.service.OrderInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

    @DubboReference
    private OrderInfoService orderInfoService;
    @DubboReference
    private AccountService accountService;
    @Test
    void contextLoads() throws InterruptedException {
        orderInfoService.generateOrder(2L, 2L);
//        accountService.addAccount(1L, 5L);
    }

}
