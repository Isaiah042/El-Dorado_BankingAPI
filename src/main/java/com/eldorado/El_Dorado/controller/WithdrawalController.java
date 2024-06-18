package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.repository.WithdrawalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WithdrawalController {

    @Autowired
    private WithdrawalRepository withdrawalRepository;
  
    private static final Logger withdrawalLogger = LoggerFactory.getLogger(WithdrawalController.class);
    @GetMapping("accounts/{accountId}/withdrawals")
    public ResponseEntity<?> getAllAccountWithdrawals(@PathVariable Long accountId){
        return null;
    }

    @GetMapping("/withdrawals/{withdrawalId}")
    public ResponseEntity<?> getWithdrawalById(@PathVariable Long withdrawalId){
        return null;
    }

    @PostMapping("accounts/{accountId}/withdrawals")
    public void makeNewWithdrawal(@PathVariable Long accountId){
    }

    @PutMapping("withdrawals/{withdrawalId}")
    public ResponseEntity<?> updateExistingWithdrawals(@PathVariable Long withdrawalId){
        return null;
    }

    @DeleteMapping("withdrawals/{withdrawalId}")
    public ResponseEntity<?> deleteExistingWithdrawal (@PathVariable Long withdrawalId){
        return null;
    }

}
