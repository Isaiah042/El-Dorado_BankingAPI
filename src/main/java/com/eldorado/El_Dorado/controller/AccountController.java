package com.eldorado.El_Dorado.controller;


import com.eldorado.El_Dorado.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.eldorado.El_Dorado.domain.Account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    private static final Logger accountLogger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        accountService.getAccountsById(accountId);
        return null;

    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
        accountService.updateAccount(accountId, account);
        return null;
    }


}
