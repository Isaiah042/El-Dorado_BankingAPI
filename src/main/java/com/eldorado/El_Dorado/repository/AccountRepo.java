package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Long> {

    @Query(value = "select a.* from account a, customer c Where a.id = ? And a.customer_id = c.id",nativeQuery = true)
    List<Account> findByCustomerId(Long customerId);
}
