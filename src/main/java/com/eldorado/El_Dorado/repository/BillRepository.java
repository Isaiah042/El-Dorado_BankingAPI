package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {
<<<<<<< HEAD
    List<Bill> findByAccountId(Long accountId);



=======




>>>>>>> b3d756b7eb2acb13a4d0eff78f8cb87ff788f77a
}
