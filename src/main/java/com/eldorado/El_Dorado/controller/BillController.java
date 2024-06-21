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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BillController {

    @Autowired
    private BillService billService;

    private static final Logger billLogger = LoggerFactory.getLogger(BillController.class);

    @GetMapping("/bills/{billId}")
    public ResponseEntity<Map<String, Object>> getBillById(@PathVariable Long billId) {
        Bill bill = billService.getBillById(billId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", bill);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts/{accountId}/bills")
    public ResponseEntity<Map<String, Object>> getBillsByAccountId(@PathVariable Long accountId) {
        List<Bill> bills = billService.getBillsForAccount(accountId);
        Map<String, Object> response = new HashMap<>();
        if (bills.isEmpty()) {
            response.put("code", 404);
            response.put("message", "Error fetching bills");
            return ResponseEntity.status(404).body(response);
        }
        response.put("code", 200);
        response.put("data", bills);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customers/{customerId}/bills")
    public ResponseEntity<List<Bill>> getAllBillsByCustomer(@PathVariable Long customerId) {
        billLogger.info("Fetching all bills for customer {}", customerId);
        List<Bill> bills = billService.getAllBillsByCustomer(customerId);
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @PostMapping("/accounts/{accountId}/bills")
    public ResponseEntity<Map<String, Object>> createBill(@PathVariable Long accountId, @RequestBody Bill bill) {
        Bill createdBill = billService.createBill(accountId, bill);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "Created bill and added it to the account");
        response.put("data", createdBill);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/bills/{billId}")
    public ResponseEntity<Map<String, Object>> updateBill(@PathVariable Long billId, @RequestBody Bill billDetails) {
        Bill updatedBill = billService.updateBill(billId, billDetails);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 202);
        response.put("message", "Accepted bill modification");
        response.put("data", updatedBill);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bills/{billId}")
    public ResponseEntity<Map<String, Object>> deleteBill(@PathVariable Long billId) {
        try {
            billService.deleteBill(billId);
            Map<String, Object> response = new HashMap<>();
            response.put("code", 204);
            response.put("message", "Bill deleted successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Error deleting bill");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}