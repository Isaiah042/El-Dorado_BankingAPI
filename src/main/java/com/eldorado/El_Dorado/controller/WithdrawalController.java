package com.eldorado.El_Dorado.controller;

import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.service.WithdrawalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    private static final Logger withdrawalLogger = LoggerFactory.getLogger(WithdrawalController.class);


}
