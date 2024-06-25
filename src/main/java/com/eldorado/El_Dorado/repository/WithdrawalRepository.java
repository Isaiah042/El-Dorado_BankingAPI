package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Withdrawal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {
    @Query(value = "select w.* from withdrawal w, where w.payer_id = ?", nativeQuery = true)
    Optional<Withdrawal> findByAccount(Long accountId);
}
