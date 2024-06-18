package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {

    @Autowired
    private BillService service;

    private static final Logger billLogger = LoggerFactory.getLogger(BillController.class);


    @GetMapping("/bills/{billId}")
    public ResponseEntity<?> getBillsById(@PathVariable Long billId){
        return null;
    }

    @GetMapping("accounts/{accountId}/bills")
    public ResponseEntity<?> getAllBills(@PathVariable Long accountId){
        return null;
    }

    @PostMapping("accounts/{accountId}/bills")
    public void createBill(@PathVariable Long accountId){
    }

    @DeleteMapping("bills/{billId}")
    public ResponseEntity<?> deleteBill (@PathVariable Long billId){
        return null;
    }

    @PutMapping("bills/{billId}")
    public ResponseEntity<?> updateBill(@PathVariable Long billId){
        return null;
    }
}
