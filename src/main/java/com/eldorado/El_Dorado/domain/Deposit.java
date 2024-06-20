package com.eldorado.El_Dorado.domain;

import com.eldorado.El_Dorado.domain.enums.Medium;
import com.eldorado.El_Dorado.domain.enums.Status;
import com.eldorado.El_Dorado.domain.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 *
 */


@Entity
public class Deposit {
    @Id
    @GeneratedValue
    @Column(name = "DEPOSIT_ID")
    private Long id;//deposit id
    @Enumerated(EnumType.STRING)
    private TransactionType type;//P2P , Deposit, Withdrawal      in the case of P2P one account withdraws and the other receives.

    @CreationTimestamp
    private Instant transaction_date;
    @Enumerated(EnumType.STRING)
    private Status status;

    private Long payee_id;
    @Enumerated(EnumType.STRING)//accountId receiving deposit
    private Medium medium;
    @NotNull
    private Double amount;
    private String description;

    //ability to deposit to a different account

    public Deposit(Long id, TransactionType type, Instant transaction_date, Status status, Long payee_id, Medium medium, Double amount, String description) {
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

    public Instant getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Instant transaction_date) {
        this.transaction_date = transaction_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getPayee_id() {
        return payee_id;
    }

    public void setPayee_id(Long payee_id) {
        this.payee_id = payee_id;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
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
