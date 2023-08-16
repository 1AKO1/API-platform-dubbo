package com.bagamao.orderservice.impl;

import com.bagamao.apicommon.service.OrderInfoService;
import com.bagamao.orderservice.OrderServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceApplication.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderInfoService orderService;

    @Test
    public void wuhu(){
        Integer integer = orderService.generateOrder(1L, 1L);
    }
}