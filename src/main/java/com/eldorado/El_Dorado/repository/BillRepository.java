package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {
    List<Bill> findByAccountId(Long accountId);



}
