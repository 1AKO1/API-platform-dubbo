package com.bagamao.tradeservice.mapper;

import com.bagamao.apicommon.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 用户数据库操作
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    public int updateAccountByUserId(Map<String, Long> params);

    public int decreaseAccountByUserId(Map<String, Long> params);
}




