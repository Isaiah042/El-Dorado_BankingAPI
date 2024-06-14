package com.eldorado.El_Dorado.controller;


import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepositController {

    @Autowired
    private DepositService depositService;


    @GetMapping("accounts/{accountId}/deposits")
    public ResponseEntity<?> getAllAccountDeposits(@PathVariable Long accountId){
        return null;
    }

    @GetMapping("/deposits/{depositId}")
    public ResponseEntity<?> getDepositById(@PathVariable Long depositId){
        return null;
    }

    @PostMapping("accounts/{accountId}/deposits")
    public void makeNewDeposit(@PathVariable Long accountId){
    }

    @PutMapping("deposits/{depositId}")
    public ResponseEntity<?> updateExistingDeposit(@PathVariable Long depositId){
        return null;
    }

    @DeleteMapping("deposits/{depositId}")
    public ResponseEntity<?> deleteExistingDeposit (@PathVariable Long depositId){
        //status 204 NO_CONTENT
        return null;
    }



    /**
     * Get deposits for a specific account
     *
     * Get deposit by id
     *
     * POST Create a deposit
     *
     * PUT Update a specified existing deposit
     *
     * DELETE Delete a specific existing deposit
     */

}
