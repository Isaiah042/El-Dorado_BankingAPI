package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.eldorado.El_Dorado.domain.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    private static final Logger accountLogger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
    @GetMapping("/accounts")
    public ResponseEntity<Account>  getAllAccounts() {
       accountService.getAllAccounts();
       return null;
    }

//    @GetMapping("/accounts/{accountId}")
//    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
//        accountService.getAccountsById(accountId);
//        return null;
//    }

    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<Account>  getAccountsByCustomerId(@PathVariable Long customerId){
        accountService.getAccountsByCustomerId(customerId);
        return null;
    }
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<Account> deleteAccountById(@PathVariable Long accountId, @RequestBody Account account){
        accountService.deleteAccount(accountId);
        return null;
    }
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> verifyAccount(@PathVariable Long accountId){
        accountService.verifyAccount(accountId);
        return null;
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
        accountService.updateAccount(accountId, account);
        return null;
    }


}
