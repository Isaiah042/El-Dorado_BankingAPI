package com.eldorado.El_Dorado.repository;

import com.eldorado.El_Dorado.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
}
