package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {
<<<<<<< HEAD
    List<Bill> findByAccountId(Long accountId);
=======
//    List<Bill> findByAccountId(Long accountId);

>>>>>>> 3e9e363dcf7e330739ad9869366b4cf95bed763e
}

