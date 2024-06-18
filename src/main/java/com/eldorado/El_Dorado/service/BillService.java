package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }







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

    protected ResponseEntity<?> updateBill(Long billId, Bill updatedBill){
     return billRepository.findById(billId).map(bill ->{
         bill.setNickName(updatedBill.getNickName());
         bill.setPayment_amount(updatedBill.getPayment_amount());
         bill.setBillStatus(updatedBill.getBillStatus());
         bill.setPayee(updatedBill.getPayee());
         bill.setPayment_date(updatedBill.getPayment_date());
         bill.setUpcoming_payment_date(updatedBill.getUpcoming_payment_date());
        billRepository.save(bill);
        return new ResponseEntity(bill, HttpStatus.OK);
    }
    ).orElseThrow(() -> new ResourceNotFoundException("Bill Id " + billId + " doesn't exist."));
        }
}


