package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    public void getAccountsById(Long accountId) {
        accountRepo.findById(accountId);
    }

    public void getAccountsByCustomerId(Long customerId) {
        customerRepo.findById(customerId);
    }


    public ResponseEntity<Object> deleteAccount(@PathVariable Long accountId) {
        return accountRepo.findById(accountId)
                .map(account -> {
                    accountRepo.delete(account);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public void verifyAccount(Long accountId) {
        Optional<Account> account = accountRepo.findById(accountId);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("Account with ID of #" + accountId + " does not exist!");
        }
    }

    public void updateAccount(Long accountId, Account accountDetails) {
        accountRepo.findById(accountId)
                .map(account -> {
                    account.setType(accountDetails.getType());
                    account.setNickname(accountDetails.getNickname());
                    account.setRewards(accountDetails.getRewards());
                    account.setBalance(accountDetails.getBalance());
                    account.setCustomer(accountDetails.getCustomer());
                    Account updatedAccount = accountRepo.save(account);
                    return ResponseEntity.ok().body(updatedAccount);
                });
        ResponseEntity.notFound().build();
    }

}



