package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    private static final Logger billLogger = LoggerFactory.getLogger(BillController.class);


    @GetMapping("bills/{billId}")
    public ResponseEntity<?> getBillById(@PathVariable Long billId) {
        billLogger.info("Fetching bill with id {}", billId);
        return billService.getBillById(billId);
    }



    @GetMapping("accounts/{accountId}/bills")
    public ResponseEntity<?> getAllBillsByAccount(@PathVariable Long accountId) {
        billLogger.info("Fetching all bills for account id {}", accountId);
        return billService.getBillsForAccount(accountId);
    }

    @GetMapping("customers/{customerId}/bills")
    public ResponseEntity<?> getAllBillsByCustomer(@PathVariable Long customerId){
        billLogger.info("Fetching all bills for customer {}",customerId);
        return billService.getAllBillsByCustomer(customerId);
    }

    @PostMapping("accounts/{account_Id}/bills")
    public ResponseEntity<?> createBill(@PathVariable Long accountId, @RequestBody Bill bill) {
        Bill createdBill = billService.createBill(accountId, bill);
        billLogger.info("Created bill for account ID: {}", accountId);
        return ResponseEntity.status(201).body(createdBill);
    }


    @DeleteMapping("bills/{billId}")
    public ResponseEntity<?> deleteBill(@PathVariable Long billId) {
        billLogger.info("Deleting bill with ID: {}", billId);
        billService.deleteBill(billId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("bills/{billId}")
    public ResponseEntity<?> updateBill(@PathVariable Long billId, @RequestBody Bill billDetails) {
        billLogger.info("Updating bill with ID: {}", billId);
        return billService.updateBill(billId, billDetails);
    }
}