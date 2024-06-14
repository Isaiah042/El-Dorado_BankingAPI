package com.eldorado.El_Dorado.domain;

import java.util.Set;

public class Customer {

    private static long idCounter = 0;
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Address> address;

    public Customer(String firstName, String lastName, Set<Address> address) {
        this.id = generateId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    private synchronized static long generateId() {
        return idCounter++;
    }

    public Long getId() {
        return id;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                '}';
    }
}
