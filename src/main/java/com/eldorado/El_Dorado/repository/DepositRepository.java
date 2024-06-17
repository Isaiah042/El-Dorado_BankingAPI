package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Deposit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
    @Query(value = "select d.* from deposit where depositId = ? and deposit.payee_id = account.accountId", nativeQuery = true)
    Iterable<Deposit> findByAccount(Long accountId);
}
