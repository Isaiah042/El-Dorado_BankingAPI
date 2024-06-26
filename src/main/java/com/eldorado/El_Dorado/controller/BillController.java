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

    @GetMapping("/bills/{billId}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long billId) {
        billLogger.info("Fetching bill with id {}", billId);
        Bill bill = billService.getBillById(billId);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/bills")
    public ResponseEntity<List<Bill>> getAllBillsByAccount(@PathVariable Long accountId) {
        billLogger.info("Fetching all bills for account id {}", accountId);
        List<Bill> bills = billService.getBillsForAccount(accountId);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/customers/{customerId}/bills")
    public ResponseEntity<List<Bill>> getAllBillsByCustomer(@PathVariable Long customerId) {
        billLogger.info("Fetching all bills for customer {}", customerId);
        List<Bill> bills = billService.getAllBillsByCustomer(customerId);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountId}/bills")
    public ResponseEntity<Bill> createBill(@PathVariable("accountId") Long accountId, @RequestBody Bill bill) {
        Bill createdBill = billService.createBill(accountId, bill);
        billLogger.info("Created bill for account ID: {}", accountId);
        return new ResponseEntity<>(createdBill, HttpStatus.CREATED);
    }

    @DeleteMapping("/bills/{billId}")
    public ResponseEntity<Void> deleteBill(@PathVariable Long billId) {
        billLogger.info("Deleting bill with ID: {}", billId);
        billService.deleteBill(billId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/bills/{billId}/{accountId}")
    public ResponseEntity<Bill> updateBill(@PathVariable Long billId, @RequestBody Bill billDetails, @PathVariable Long accountId) {
        billLogger.info("Updating bill with ID: {}", billId);
        Bill updatedBill = billService.updateBill(billId, billDetails, accountId);
        return new ResponseEntity<>(updatedBill, HttpStatus.OK);
    }
}
