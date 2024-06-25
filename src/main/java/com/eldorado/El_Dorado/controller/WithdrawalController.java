package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.exception.TransactionFailedException;
import com.eldorado.El_Dorado.service.WithdrawalService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.eldorado.El_Dorado.response.ResponseHandler;
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
    private WithdrawalService withdrawalService;
  
    private static final Logger withdrawalLogger = LoggerFactory.getLogger(WithdrawalController.class);

    @GetMapping("/withdrawals")
    public Iterable<Withdrawal> getAllWithdrawals() {
        Iterable<Withdrawal> withdrawals = withdrawalService.getAllWithdrawals();
        return withdrawals;
    }
    @GetMapping("accounts/{accountId}/withdrawals")
    public Optional<Withdrawal> getAllAccountWithdrawalsByAccountId(@PathVariable Long accountId){
        return withdrawalService.getWithdrawalById(accountId);
    }

    @GetMapping("/withdrawals/{withdrawalId}")
    public Optional<Withdrawal> getWithdrawalById(@PathVariable Long withdrawalId){
        return withdrawalService.getWithdrawalById(withdrawalId);
    }

    @PostMapping("accounts/{accountId}/withdrawals")
    public ResponseEntity<?> makeNewWithdrawal(@PathVariable Long accountId,@Valid @RequestBody Withdrawal withdrawal) {
        return new ResponseEntity<>(withdrawalService.makeWithdrawal(accountId, withdrawal), HttpStatus.CREATED);
    }

    @PutMapping("withdrawals/{withdrawalId}")
    public ResponseEntity<?> updateExistingWithdrawals(@PathVariable Long withdrawalId,@Valid @RequestBody Withdrawal withdrawal){
        withdrawalService.updateWithdrawal(withdrawalId, withdrawal);
        return ResponseHandler.responseBuilder("Accepted deposit modification", HttpStatus.ACCEPTED);
    }

    //ResponseEntity<?>
//    @DeleteMapping("withdrawals/{withdrawalId}")
//    public ResponseEntity<?> deleteExistingWithdrawal (@PathVariable Long withdrawalId){
//        withdrawalService.deleteWithdrawalById(withdrawalId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
