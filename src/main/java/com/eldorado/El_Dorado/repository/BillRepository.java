package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {
<<<<<<< HEAD
    List<Bill> findByAccountId(Long accountId);
=======



>>>>>>> 56c8f12752b113b7e64d636ff0b6a2ba30399166
}
