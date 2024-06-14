package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BillService {

    @Autowired
    private BillRepository billRepository;

    public Optional<Bill> getBillById(Long billID){
        return billRepository.findById(billID);
    }

    public Iterable<Bill> getAllBills(){
        return billRepository.findAll();
    }

    public void deleteBill(Long billID){
        Bill currentBalance = billRepository.findById(billID)
                .orElseThrow(() -> new ResourceNotFoundException("Bill with id " + billID + " does not exist :)"));
    }

    protected void verifyBill(Long billId) throws ResourceNotFoundException{
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isEmpty()){
            throw new ResourceNotFoundException("Bill with id " + billId + "does not exist");
        }
    }

    }

