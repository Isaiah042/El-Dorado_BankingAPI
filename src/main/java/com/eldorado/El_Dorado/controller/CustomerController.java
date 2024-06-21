package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Address;
import com.eldorado.El_Dorado.domain.Customer;
import com.eldorado.El_Dorado.response.ResponseHandler;
import com.eldorado.El_Dorado.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger customerLogger = LoggerFactory.getLogger(Customer.class);

    @GetMapping("/accounts/{accountId}/customers")
    public ResponseEntity<?> getCustomerByAccountId(@PathVariable Long accountId) {

        Optional<Customer> customer = customerService.getCustomerByAccountId(accountId);

        if(customer == null){
            return ResponseHandler.responseBuilder(
                    "Error fetching customers accounts.", HttpStatus.NOT_FOUND, null);
        }else
            return ResponseHandler.responseBuilder("Success", HttpStatus.OK, customer);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<?> getCustomerByCustomerId(@PathVariable Long customerId) {
        Optional<Customer> customer = customerService.getCustomerByCustomerId(customerId);

        if(customer == null){
            return ResponseHandler.responseBuilder(
                    "Error fetching customers accounts.", HttpStatus.NOT_FOUND, null);
        }else
            return ResponseHandler.responseBuilder("Success", HttpStatus.OK, customer);
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        Iterable<Customer> customer = customerService.getAllCustomers();

        if(customer == null){
            return ResponseHandler.responseBuilder(
                    "Error fetching accounts.", HttpStatus.NOT_FOUND, null);
        }else
            return ResponseHandler.responseBuilder("Success", HttpStatus.OK, customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customerToBeCreated) {
        try {
            customerService.createCustomer(customerToBeCreated);
            return ResponseHandler.responseBuilder("Success", HttpStatus.OK, customerToBeCreated);
        } catch (Exception e) {
                return ResponseHandler.responseBuilder("Error.", HttpStatus.NOT_FOUND, null);
        }

    }

    @PutMapping("/customers/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customerToBeUpdated) {

        try {
            customerService.updateCustomer(customerId, customerToBeUpdated);
            return ResponseHandler.responseBuilder("Success", HttpStatus.OK, customerToBeUpdated);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder("Error.", HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
            try {
                customerService.deleteCustomer(customerId);
                return ResponseHandler.responseBuilder("Success", HttpStatus.OK, customerId);
            } catch (Exception e) {
                return ResponseHandler.responseBuilder(
                        "Error.", HttpStatus.NOT_FOUND, null);
            }
    }
}
