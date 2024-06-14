package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.domain.Customer;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccountService accountService;

    public Optional<Customer> getCustomerByAccountId(Long accountId) {
        return customerRepo.findById(accountId);
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    protected void verifyCustomer(Long customerId) throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepo.findById(customerId);

        if(customer.isEmpty()) {
            throw new ResourceNotFoundException("Customer with ID #" + customerId + " does not exist.");
        }
    }

    public Customer createCustomer(Customer customerToBeCreated) {
        return customerRepo.save(customerToBeCreated);
    }

    public Customer updateCustomer(Long customerId, Customer customerToBeUpdated) {
        verifyCustomer(customerId);
        customerToBeUpdated.setId(customerId);
        return customerRepo.save(customerToBeUpdated);
    }

    public void deleteCustomer(Long customerId) {
        verifyCustomer(customerId);
        customerRepo.deleteById(customerId);
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        verifyCustomer(customerId);
        return customerRepo.findById(customerId);
    }

    public Customer getCustomerByName(String firstName, String lastName) throws ResourceNotFoundException {
        boolean emptyString = firstName.isEmpty() && lastName.isEmpty();

        if (emptyString) {
            throw (new ResourceNotFoundException("Please enter a first and last name."));
        } else {
            for (Customer customer : this.customerRepo.findAll()) {
                if (customer.getFirstName().equalsIgnoreCase(firstName) && customer.getLastName().equalsIgnoreCase(lastName)) {
                    return customer;
                }
            }
        }
        throw (new ResourceNotFoundException("Customer '" + firstName + lastName +  "' was not found"));
    }
}
