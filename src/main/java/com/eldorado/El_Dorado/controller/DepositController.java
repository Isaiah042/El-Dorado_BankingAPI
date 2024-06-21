package com.eldorado.El_Dorado.controller;


import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.exception.TransactionFailedException;
import com.eldorado.El_Dorado.response.ResponseHandler;
import com.eldorado.El_Dorado.service.DepositService;
import jakarta.transaction.TransactionRolledbackException;
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
        try {
            Iterable<Deposit> allDeposits = depositService.getAllDeposits(accountId);
            if(!allDeposits.iterator().hasNext())
                return ResponseHandler.responseBuilder(
                        "Account not found",
                        HttpStatus.NOT_FOUND);
            return ResponseHandler.responseBuilder(
                    "Success",
                    HttpStatus.OK,
                    allDeposits);
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/deposits/{depositId}")
    public ResponseEntity<?> getDepositById(@PathVariable Long depositId) throws ResourceNotFoundException {
        Deposit deposit = depositService.getById(depositId);
        if(deposit == null)
            return ResponseHandler.responseBuilder(
                    "Error fetching deposit with id: " + depositId,
                    HttpStatus.NOT_FOUND);
        return ResponseHandler.responseBuilder(
                "Success",
                HttpStatus.OK,
                deposit);
    }

    @PostMapping("accounts/{accountId}/deposits")
    public ResponseEntity<?> makeNewDeposit(@PathVariable Long accountId, @RequestBody Deposit deposit) throws TransactionRolledbackException {
        try {
            Deposit newDeposit = depositService.makeDeposit(accountId, deposit);
            if(deposit == null)
                return ResponseHandler.responseBuilder(
                        "Error creating deposit",
                        HttpStatus.NOT_FOUND);
            return ResponseHandler.responseBuilder(
                    "Created deposit and added it to the account",
                    HttpStatus.CREATED,
                    deposit);
        } catch (TransactionRolledbackException e) {
            throw new TransactionFailedException();
        }
    }

    @PutMapping("deposits/{depositId}/{accountId}")
    public ResponseEntity<?> updateExistingDeposit(@PathVariable Long depositId, @RequestBody Deposit deposit, @RequestBody Long accountId) throws TransactionRolledbackException {
        try {
            Deposit updatedDeposit = depositService.updateDeposit(depositId, deposit, accountId);
            return ResponseHandler.responseBuilder(
                    "Accepted deposit modification",
                    HttpStatus.ACCEPTED,
                    updatedDeposit);
        } catch (TransactionRolledbackException e) {
            return ResponseHandler.responseBuilder(
                    "Deposit ID does not exist",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("deposits/{depositId}/{accountId}")
    public ResponseEntity<?> deleteExistingDeposit (@PathVariable Long depositId, @PathVariable Long accountId) throws TransactionRolledbackException {
        Deposit reversedDeposit = depositService.deleteDeposit(depositId, accountId);
        if(reversedDeposit == null)
            return ResponseHandler.responseBuilder(
                    "Deposit id does not exist",
                    HttpStatus.NOT_FOUND);
        return ResponseHandler.responseBuilder(
                "Deleted successfully",
                HttpStatus.NO_CONTENT);
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
     *
     *
     * @GetMapping("/{id}")
     * public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
     *    Employee emp = employeeService.findEmployeeById(id);
     *    if(emp != null)
     *         return ResponseEntity.status(HttpStatus.OK).body(emp);
     *    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
     * }
     */



}
