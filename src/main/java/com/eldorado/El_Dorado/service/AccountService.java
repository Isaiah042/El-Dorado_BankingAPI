package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Account;

import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.CustomerRepo;
import com.eldorado.El_Dorado.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Account getAccountsById(Long accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Error fetching account with this id"));
    }


    public List<Account> getAccountsByCustomerId(Long customerId) {
        List<Account> accounts = accountRepo.findByCustomerId(customerId);
        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("Error fetching accounts with this customer id");
        }
        return accounts;
    }

    public ResponseEntity<?> deleteAccount(Long accountId){
       Account accountToDelete = accountRepo.findById(accountId).orElse(null);
       if(accountToDelete == null){
           throw new ResourceNotFoundException("This account does not exist");
       }else {
           accountRepo.delete(accountToDelete);
           return ResponseHandler.responseBuilder("Account successfully deleted", HttpStatus.ACCEPTED);

       }
    }


    public void verifyAccount(Long accountId) {
        Optional<Account> account = accountRepo.findById(accountId);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("Account with ID of #" + accountId + " does not exist!");
        }
    }

    public ResponseEntity<Account> updateAccount(Long accountId, Account accountDetails) {
        return accountRepo.findById(accountId)
                .map(account -> {
                    account.setType(accountDetails.getType());
                    account.setNickname(accountDetails.getNickname());
                    account.setRewards(accountDetails.getRewards());
                    account.setBalance(accountDetails.getBalance());
                    account.setCustomer(accountDetails.getCustomer());
                    Account updatedAccount = accountRepo.save(account);
                    return ResponseEntity.ok().body(updatedAccount);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}



