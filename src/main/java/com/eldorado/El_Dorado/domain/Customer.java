package com.eldorado.El_Dorado.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty
    @Column(name = "lastName")
    private String lastName;
    private Set<Address> address;
    private Set<Account> accounts;

    public Customer(Long id, String firstName, String lastName, Set<Address> address, Set<Account> accounts) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

        this.accounts = accounts;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
