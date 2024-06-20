package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
    @Query(value = "SELECT c.* FROM customer c, account a WHERE a.id = ? and c.id = a.customer_id", nativeQuery = true)
    Optional<Customer> findCustomerByAccountId(@Param("account_id") Long accountId);
}
