package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Customer;
import com.eldorado.El_Dorado.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    private static final Logger customerLogger = LoggerFactory.getLogger(Customer.class);


}
