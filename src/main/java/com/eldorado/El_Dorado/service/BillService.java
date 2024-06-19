package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.BillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private BillRepository billRepository;

    @Transactional
    public Bill createBill(Long accountId, Bill bill) {
        return billRepository.save(bill);
    }

    public ResponseEntity<?> getBillById(Long billID){
        Bill getBill = billRepository.findById(billID).orElse(null);
        if (getBill == null){
            throw new ResourceNotFoundException("Error getting bill ID");
        }else return new ResponseEntity<>(billID, HttpStatus.OK);

    }

    public ResponseEntity<?> getAllBills(Long accountId){
        List<Bill> allBills = billRepository.findByAccountId(accountId);
        if(allBills == null){
            throw new ResourceNotFoundException("Account can't be find");
        }else return new ResponseEntity<>(allBills,HttpStatus.OK);
    }
    public void getBillsForAccount(Long accountId) {
         billRepository.findById(accountId);
    }
//    public void deleteBill(Long billID){
//        Bill currentBalance = billRepository.findById(billID)
//                .orElseThrow(() -> new ResourceNotFoundException("Bill with id " + billID + " does not exist :)"));
//    }

    protected void verifyBill(Long billId) throws ResourceNotFoundException{
        Optional<Bill> bill = billRepository.findById(billId);
        if (bill.isEmpty()){
            throw new ResourceNotFoundException("Bill with id " + billId + "does not exist");
        }
    }

    public ResponseEntity<?> updateBill(Long billId, Bill updatedBill){
     return billRepository.findById(billId).map(bill ->{
         bill.setNickName(updatedBill.getNickName());
         bill.setPayment_amount(updatedBill.getPayment_amount());
         bill.setBillStatus(updatedBill.getBillStatus());
         bill.setBillPayee(updatedBill.getBillPayee());
         bill.setPayment_date(updatedBill.getPayment_date());
         bill.setUpcoming_payment_date(updatedBill.getUpcoming_payment_date());
        billRepository.save(bill);
        return new ResponseEntity(bill, HttpStatus.OK);
    }
    ).orElseThrow(() -> new ResourceNotFoundException("Bill Id " + billId + " doesn't exist."));
        }
}


