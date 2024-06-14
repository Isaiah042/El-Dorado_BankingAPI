package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;


    public void getAccountsById(Long id) {
        accountRepo.findById(id);
    }
    public Iterable<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountRepo.save(account);
    }


    //Variables here FOLLOW THE BOOK

    //Put Account Methods in here

    //Setters and Getters

}
