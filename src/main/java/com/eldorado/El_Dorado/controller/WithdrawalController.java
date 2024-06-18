package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.repository.WithdrawalRepository;
<<<<<<< HEAD
import com.eldorado.El_Dorado.service.WithdrawalService;
=======
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> f51594963182864abae3d23f68d4d2ff21fac4e2
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;


@RestController
public class WithdrawalController extends WithdrawalService {

    @Autowired
<<<<<<< HEAD
    private WithdrawalService withdrawalService;
=======
    private WithdrawalRepository withdrawalRepository;
  
    private static final Logger withdrawalLogger = LoggerFactory.getLogger(WithdrawalController.class);
>>>>>>> f51594963182864abae3d23f68d4d2ff21fac4e2
    @GetMapping("accounts/{accountId}/withdrawals")
    public Optional<Withdrawal> getAllAccountWithdrawalsByAccountId(@PathVariable Long accountId){
        return withdrawalService.getWithdrawalById(accountId);
    }

    @GetMapping("/withdrawals/{withdrawalId}")
    public Optional<Withdrawal> getWithdrawalById(@PathVariable Long withdrawalId){
        return withdrawalService.getWithdrawalById(withdrawalId);
    }

    @PostMapping("accounts/{accountId}/withdrawals")
    public ResponseEntity<?> makeNewWithdrawal(Withdrawal withdrawal){
        withdrawalService.saveWithdrawal(withdrawal);
        return null;
    }

    @PutMapping("withdrawals/{withdrawalId}")
    public ResponseEntity<?> updateExistingWithdrawals(@PathVariable Long withdrawalId, @RequestBody Withdrawal withdrawal){
        withdrawalService.updateWithdrawal(withdrawalId, withdrawal);
        return null;
    }

    //ResponseEntity<?>
    @DeleteMapping("withdrawals/{withdrawalId}")
    public ResponseEntity<?> deleteExistingWithdrawal (@PathVariable Long withdrawalId){
        withdrawalService.deleteWithdrawalById(withdrawalId);
        return null;
    }

}
