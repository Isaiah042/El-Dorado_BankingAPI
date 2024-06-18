package com.eldorado.El_Dorado.domain;


import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer {

    private Long id;

    @NotEmpty
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty
    @Column(name = "lastName")
    private String lastName;

    private static long idCounter = 0;

    @OneToMany
    private Set<Address> address;


    @OneToMany
    private Set<Account> accounts;

    public Customer(Long id, String firstName, String lastName, Set<Address> address, Set<Account> accounts) {
    public Customer(Long id, String firstName, String lastName, Set<Address> address) {
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

    public static long getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(long idCounter) {
        Customer.idCounter = idCounter;
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
