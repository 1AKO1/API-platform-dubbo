package com.bagamao.orderservice.impl;

import com.bagamao.apicommon.entity.OrderInfo;
import com.bagamao.apicommon.exception.BusinessException;
import com.bagamao.apicommon.service.OrderInfoService;
import com.bagamao.apicommon.util.JsonResult;
import com.bagamao.orderservice.mapper.OrderInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderMapper;

    @Override
    public Integer generateOrder(Long userId, Long interfaceInfoId){
        OrderInfo order = new OrderInfo();
        order.setUserId(userId);
        order.setInterfaceInfoId(interfaceInfoId);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orderMapper.insert(order);
    }
}
