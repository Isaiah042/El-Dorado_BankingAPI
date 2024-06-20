package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Deposit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
    @Query(value = "select d.* from account a, deposit d where d.deposit_id = ? and d.payee_id = a.id", nativeQuery = true)
    List<Deposit> findByAccount(Long accountId);
}
