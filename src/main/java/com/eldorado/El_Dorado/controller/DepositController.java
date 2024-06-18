package com.eldorado.El_Dorado.controller;


import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.response.ResponseHandler;
import com.eldorado.El_Dorado.service.DepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepositController {


    private final DepositService depositService;

    @Autowired
    public DepositController(DepositService depositService){
        this.depositService = depositService;
    }

    private static final Logger depositLogger = LoggerFactory.getLogger(DepositController.class);



    @GetMapping("accounts/{accountId}/deposits")
    public ResponseEntity<?> getAllAccountDeposits(@PathVariable Long accountId){
        depositService.getAllDeposits(accountId);
        return null;
    }

    @GetMapping("/deposits/{depositId}")
    public ResponseEntity<?> getDepositById(@PathVariable Long depositId){
        depositService.getById(depositId);
        return null;
    }

    @PostMapping("accounts/{accountId}/deposits")
    public ResponseEntity<?> makeNewDeposit(@PathVariable Long accountId, @RequestBody Deposit deposit){
        depositService.makeDeposit(accountId, deposit);
        return null;
    }

    @PutMapping("deposits/{depositId}")
    public ResponseEntity<?> updateExistingDeposit(@PathVariable Long depositId, @RequestBody Deposit deposit){
        return ResponseHandler.responseBuilder(
                "Accepted deposit modification",
                HttpStatus.ACCEPTED,
                depositService.updateDeposit(depositId, deposit));
    }

    @DeleteMapping("deposits/{depositId}")
    public ResponseEntity<?> deleteExistingDeposit (@PathVariable Long depositId){
        //status 204 NO_CONTENT
        depositService.deleteDeposit(depositId);
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
