package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Account;
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
    private BillRepository billRepository;

    @Transactional
    public Bill createBill(Long accountId, Bill bill) {
        bill.setAccount_id(bill.getAccount_id());
        return billRepository.save(bill);
    }

    public Bill getBillById(Long billID) {
        return billRepository.findById(billID)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID " + billID));
    }

    public List<Bill> getBillsForAccount(Long accountId) {
        return billRepository.findBillsByAccountId(accountId);
    }

    public void deleteBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill with id " + billId + " does not exist"));
        billRepository.delete(bill);
    }

    public Bill updateBill(Long billId, Bill updatedBill) {
        return billRepository.findById(billId).map(bill -> {
            bill.setNickName(updatedBill.getNickName());
            bill.setPayment_amount(updatedBill.getPayment_amount());
            bill.setBillStatus(updatedBill.getBillStatus());
            bill.setBillPayee(updatedBill.getBillPayee());
            bill.setPayment_date(updatedBill.getPayment_date());
            bill.setUpcoming_payment_date(updatedBill.getUpcoming_payment_date());
            return billRepository.save(bill);
        }).orElseThrow(() -> new ResourceNotFoundException("Bill Id " + billId + " doesn't exist."));
    }

    public List<Bill> getAllBillsByCustomer(Long customerId) {
        // Implement filtering by customerId if needed
        return billRepository.findAll();
    }
}
