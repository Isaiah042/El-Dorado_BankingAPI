package com.eldorado.El_Dorado.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


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


    @OneToMany
    @JoinColumn(name= "CUSTOMER_ID")
    private Set<Address> addresses;


    @OneToMany(mappedBy = "customer")
    @JoinColumn(name= "CUSTOMER_ID" )
    private Set<Account> accounts;

    public Customer() {
    }

    public Customer(Long id, String firstName, String lastName, Set<Address> addresses, Set<Account> accounts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = addresses;
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty String lastName) {
        this.lastName = lastName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
