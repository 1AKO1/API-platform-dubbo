package com.bagamao.tradeservice.controller;


import com.bagamao.apicommon.entity.User;
import com.bagamao.apicommon.service.AccountService;
import com.bagamao.apicommon.service.UserService;
import com.bagamao.apicommon.util.JsonResult;
import com.bagamao.tradeservice.model.dto.AccountUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    private AccountService accountService;

    @PostMapping ("/account/add")
    public JsonResult addAcount(@RequestBody AccountUpdateDTO accountUpdateDTO){
        Integer i = accountService.addAccount(accountUpdateDTO.getUserId(), accountUpdateDTO.getChangeNum());
        if(i > 0)
            return JsonResult.ok();
        else
            return JsonResult.err();
    }
}
