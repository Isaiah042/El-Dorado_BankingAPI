package com.eldorado.El_Dorado.domain;

import com.eldorado.El_Dorado.domain.enums.Medium;
import com.eldorado.El_Dorado.domain.enums.Status;
import com.eldorado.El_Dorado.domain.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPOSIT_ID")
    private Long id;//deposit id
    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType type;//P2P , Deposit, Withdrawal      in the case of P2P one account withdraws and the other receives.

    @CreationTimestamp
    private LocalDateTime transaction_date;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)//accountId receiving deposit
    @NotNull
    private Medium medium;
    @NotNull
    private Double amount;
    private String description;
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    @OnDelete( action = OnDeleteAction.CASCADE)
    private Account payee;

    public Deposit() {
    }

    public Deposit(Long id, TransactionType type, LocalDateTime transaction_date, Status status, Medium medium, Double amount, String description, Account payee) {
        this.id = id;
        this.type = type;
        this.transaction_date = transaction_date;
        this.status = status;
        this.medium = medium;
        this.amount = amount;
        this.description = description;
        this.payee = payee;
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

    public LocalDateTime getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDateTime transaction_date) {
        this.transaction_date = transaction_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Account getPayee() {
        return payee;
    }

    public void setPayee(Account payee) {
        this.payee = payee;
    }
}
