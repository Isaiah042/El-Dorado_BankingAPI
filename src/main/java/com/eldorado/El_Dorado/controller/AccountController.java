package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountController {

    @Autowired
    private AccountService accountService;

    private static final Logger accountLogger = LoggerFactory.getLogger(AccountController.class);


}
