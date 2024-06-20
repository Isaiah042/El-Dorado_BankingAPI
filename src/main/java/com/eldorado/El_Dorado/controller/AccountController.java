package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Customer;
import com.eldorado.El_Dorado.response.ResponseHandler;
import com.eldorado.El_Dorado.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.eldorado.El_Dorado.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    private static final Logger accountLogger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping("/customers/{customerId}/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/accounts")
    public Iterable<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> AccountById(@PathVariable Long accountId) {
        return new ResponseEntity<>(accountService.getAccountsById(accountId), HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/accounts")
    public List<Account> getAccountsByCustomerId(@PathVariable Long customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return accounts;
    }

    @DeleteMapping("/accounts/{accountId}")

    public ResponseEntity<?> deleteAccountById(@PathVariable Long accountId) {
        return ResponseHandler.responseBuilder("Account successfully deleted", HttpStatus.NO_CONTENT, accountService.deleteAccount(accountId));
    }

        public ResponseEntity<?> deleteAccountById (@PathVariable Long accountId, @RequestBody Account account){
            return accountService.deleteAccount(accountId);

        }

        @PutMapping("/accounts/{accountId}")
        public ResponseEntity<Account> updateAccount (@PathVariable Long accountId, @RequestBody Account account){
            return accountService.updateAccount(accountId, account);
        }

    }

