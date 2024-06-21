package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.account_id = :account_id")
    List<Bill> findBillsByAccountId(@Param("account_id") Long accountId);
}