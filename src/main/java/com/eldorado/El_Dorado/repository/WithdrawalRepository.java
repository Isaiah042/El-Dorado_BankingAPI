package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Withdrawal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {
}
