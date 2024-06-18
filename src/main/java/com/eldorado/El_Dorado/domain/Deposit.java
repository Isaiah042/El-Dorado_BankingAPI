package com.eldorado.El_Dorado.domain;

import com.eldorado.El_Dorado.domain.enums.TransactionType;
import jakarta.persistence.*;


@Entity
public class Deposit {
    @Id
    @GeneratedValue
    private Long id;     //deposit id
    private TransactionType type;    //P2P , Deposit, Withdrawal      in the case of P2P one account withdraws and the other receives.
    private String transaction_date;
    private String status;
    private Long payee_id;  //accountId receiving deposit
    private String medium;
    private Double amount;
    private String description;

    //ability to deposit to a different account

    public Deposit(Long id, TransactionType type, String transaction_date, String status, Long payee_id, String medium, Double amount, String description) {
        this.id = id;
        this.type = type;
        this.transaction_date = transaction_date;
        this.status = status;
        this.payee_id = payee_id;
        this.medium = medium;
        this.amount = amount;
        this.description = description;
    }

    public Deposit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPayee_id() {
        return payee_id;
    }

    public void setPayee_id(Long payee_id) {
        this.payee_id = payee_id;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
