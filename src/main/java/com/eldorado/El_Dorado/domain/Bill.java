package com.eldorado.El_Dorado.domain;


import com.eldorado.El_Dorado.domain.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill {
    private Long BillId;
    
    private Status billStatus;

    private String nickMame;

    private LocalDateTime creation_date;

    private LocalDateTime payment_date;

    private Integer recurring_date;

    private LocalDate upcoming_payment_date;

    private Double payment_amount;

    private Customer account_id;

}
