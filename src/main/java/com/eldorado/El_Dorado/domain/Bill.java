package com.eldorado.El_Dorado.domain;
import com.eldorado.El_Dorado.domain.enums.Medium;
import jakarta.persistence.*;

import java.time.LocalDate;
import com.eldorado.El_Dorado.domain.enums.Status;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Bill{

    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;
    
    private Status billStatus;

    private String billPayee;

    private String nickName;

    private String creation_date;

    private String payment_date;

    private Integer recurring_date;

    private String upcoming_payment_date;

    private Double payment_amount;
//    @ManyToOne
//    @JoinColumn(name = "account_id")
    private String account_id;

    @Enumerated(EnumType.STRING)
    private Medium medium;

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Status getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Status billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillPayee() {
        return billPayee;
    }

    public void setBillPayee(String billPayee) {
        this.billPayee = billPayee;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public Integer getRecurring_date() {
        return recurring_date;
    }

    public void setRecurring_date(Integer recurring_date) {
        this.recurring_date = recurring_date;
    }

    public String getUpcoming_payment_date() {
        return upcoming_payment_date;
    }

    public void setUpcoming_payment_date(String upcoming_payment_date) {
        this.upcoming_payment_date = upcoming_payment_date;
    }

    public Double getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(Double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }


    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
}