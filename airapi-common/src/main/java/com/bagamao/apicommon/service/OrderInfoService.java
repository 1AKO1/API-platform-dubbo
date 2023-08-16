package com.bagamao.apicommon.service;

public interface OrderInfoService {
    public Integer generateOrder(Long userId, Long interfaceInfoId) throws InterruptedException;
}
