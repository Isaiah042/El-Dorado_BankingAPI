package com.eldorado.El_Dorado.domain;


import com.eldorado.El_Dorado.domain.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.*;



@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private Integer rewards;
    private Double balance;

    private Customer customer;
    private AccountType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getRewards() {
        return rewards;
    }

    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
