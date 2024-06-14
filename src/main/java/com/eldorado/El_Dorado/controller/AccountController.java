package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.CustomerRepo;
import com.eldorado.El_Dorado.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.;

    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
    return null;
    }
    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        return null;
    }

}
