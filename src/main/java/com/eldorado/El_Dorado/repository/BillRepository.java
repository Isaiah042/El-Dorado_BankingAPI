package com.eldorado.El_Dorado.repository;


import com.eldorado.El_Dorado.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

<<<<<<< HEAD
public interface BillRepository extends CrudRepository<Bill, Long> {
    List<Bill> findByAccountId(Long accountId);
//    List<Bill> findByAccountId(Long accountId);

=======
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.account_id = :account_id")
    List<Bill> findBillsByAccountId(@Param("account_id") String accountId);
>>>>>>> 0fae52b5634cfff875609319f1c382557dfdbe62
}


