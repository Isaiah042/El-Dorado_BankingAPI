package com.eldorado.El_Dorado.domain;



import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;

import com.eldorado.El_Dorado.domain.enums.Status;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill {

    @Id
    @GeneratedValue
    private Long BillId;
    
    private Status billStatus;

    private String payee;

    private String nickName;

    private LocalDateTime creation_date;

    private LocalDateTime payment_date;

    private Integer recurring_date;

    private LocalDate upcoming_payment_date;

    private Double payment_amount;

    private Customer account_id;

    public Long getBillId() {
        return BillId;
    }

    public void setBillId(Long billId) {
        BillId = billId;
    }

    public Status getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Status billStatus) {
        this.billStatus = billStatus;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickMame() {
        return nickName;
    }

    public void setNickMame(String nickMame) {
        this.nickName = nickMame;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDateTime getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(LocalDateTime payment_date) {
        this.payment_date = payment_date;
    }

    public Integer getRecurring_date() {
        return recurring_date;
    }

    public void setRecurring_date(Integer recurring_date) {
        this.recurring_date = recurring_date;
    }

    public LocalDate getUpcoming_payment_date() {
        return upcoming_payment_date;
    }

    public void setUpcoming_payment_date(LocalDate upcoming_payment_date) {
        this.upcoming_payment_date = upcoming_payment_date;
    }

    public Double getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(Double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public Customer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Customer account_id) {
        this.account_id = account_id;
    }
}


