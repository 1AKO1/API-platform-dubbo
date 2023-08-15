package com.bagamao.apicommon.service;

public interface AccountService {
    public Integer addAccount(Long userId, Long changeNum);

    public Integer decreaseAccount(Long userId, Long changeNum);
}
