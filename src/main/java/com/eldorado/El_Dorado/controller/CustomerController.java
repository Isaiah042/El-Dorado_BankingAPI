package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Customer;
import com.eldorado.El_Dorado.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger customerLogger = LoggerFactory.getLogger(Customer.class);

    @GetMapping("/accounts/{accountId}/customers")
    public ResponseEntity<?> getCustomerByAccountId(@PathVariable Long accountId) {
        customerService.getCustomerByAccountId(accountId);
        return null;
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> getCustomerByCustomerId(@PathVariable Long customerId) {
        customerService.getCustomerByCustomerId(customerId);
        return null;
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        customerService.getAllCustomers();
        return null;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customerToBeCreated) {
        customerService.createCustomer(customerToBeCreated);
        return null;
    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customerToBeUpdated) {
        customerService.updateCustomer(customerId, customerToBeUpdated);
        return null;
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return null;
    }
}
