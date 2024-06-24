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
    public ResponseEntity<Object> getAllAccountDeposits(@PathVariable Long accountId){
        try {
            Iterable<Deposit> allDeposits = depositService.getAllDeposits(accountId);
            if(allDeposits.iterator().hasNext())
                depositLogger.info("DepositController getAllAccountDeposits() success! {}", allDeposits);
            return ResponseHandler.responseBuilder(
                    "Success",
                    HttpStatus.OK,
                    allDeposits);
        } catch (Exception e) {
            depositLogger.info("DepositController getAllAccountDeposits() failed request!", e);
            return ResponseHandler.responseBuilder(
                    "Account not found",
                    HttpStatus.NOT_FOUND, e);
        }
    }

    @GetMapping("/deposits/{depositId}")
    public ResponseEntity<Object> getDepositById(@PathVariable Long depositId){
        try {
            Deposit deposit = depositService.getById(depositId);
            if(deposit != null)
                depositLogger.info("DepositController getDepositById() success! {}", deposit);
            return ResponseHandler.responseBuilder(
                    "Success",
                    HttpStatus.OK,
                    deposit);
        } catch (Exception e) {
            depositLogger.info("DepositController getDepositById() failed request!", e);
            return ResponseHandler.responseBuilder(
                    "Error fetching deposit with id: " + depositId,
                    HttpStatus.NOT_FOUND, e);
        }
    }

    @PostMapping("accounts/{accountId}/deposits")
    public ResponseEntity<Object> makeNewDeposit(@PathVariable Long accountId, @RequestBody Deposit deposit){
        try {
            Deposit newDeposit = depositService.makeDeposit(accountId, deposit);
            if(deposit != null)
                depositLogger.info("DepositController makeNewDeposit() success! {}", newDeposit);
            return ResponseHandler.responseBuilder(
                    "Created deposit and added it to the account",
                    HttpStatus.CREATED,
                    newDeposit);
        } catch (Exception e) {
            depositLogger.info("DepositController makeNewDeposit() failed request!", e);
            return ResponseHandler.responseBuilder(
                    "Error creating deposit",
                    HttpStatus.NOT_FOUND, e);
        }
    }

    @PutMapping("deposits/{depositId}/{accountId}")
    public ResponseEntity<Object> updateExistingDeposit(@PathVariable Long depositId, @RequestBody Deposit deposit, @PathVariable Long accountId){
        try {
            Deposit updatedDeposit = depositService.updateDeposit(depositId, deposit, accountId);
            if(updatedDeposit != null)
                depositLogger.info("DepositController updateExistingDeposit() success! {}", updatedDeposit);
            return ResponseHandler.responseBuilder(
                    "Accepted deposit modification",
                    HttpStatus.ACCEPTED,
                    updatedDeposit);
        } catch (Exception e) {
            depositLogger.info("DepositController updateExistingDeposit() failed request!", e);
            return ResponseHandler.responseBuilder(
                    "Deposit ID does not exist",
                    HttpStatus.NOT_FOUND, e);
        }
    }

    @DeleteMapping("deposits/{depositId}/{accountId}")
    public ResponseEntity<Object> deleteExistingDeposit (@PathVariable Long depositId, @PathVariable Long accountId){
        try {
            depositService.deleteDeposit(depositId, accountId);
            depositLogger.info("DepositController deleteExistingDeposit() success!");
            return ResponseHandler.responseBuilder(
                    "Deleted successfully",
                    HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            depositLogger.info("DepositController deleteExistingDeposit() failed request!", e);
            return ResponseHandler.responseBuilder(
                    "Deposit id does not exist",
                    HttpStatus.NOT_FOUND);
        }
    }
}
