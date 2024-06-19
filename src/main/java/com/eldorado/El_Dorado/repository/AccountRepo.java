package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Long> {

    @Query(value = "select a.* from account Where account.id = ? And account.customer_id = customer.id",nativeQuery = true)
    List<Account> findByCustomer(Long customerId);
}
