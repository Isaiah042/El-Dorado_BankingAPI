package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill updateBill(Long billId, Bill billDetails) {
        Bill bill = billRepository.findById(billId).orElse(null);
        if (bill != null) {
            bill.setBillStatus(billDetails.getBillStatus());
            bill.setBillPayee(billDetails.getBillPayee());
            bill.setNickName(billDetails.getNickName());
            bill.setCreation_date(billDetails.getCreation_date());
            bill.setPayment_date(billDetails.getPayment_date());
            bill.setRecurring_date(billDetails.getRecurring_date());
            bill.setUpcoming_payment_date(billDetails.getUpcoming_payment_date());
            bill.setPayment_amount(billDetails.getPayment_amount());
            return billRepository.save(bill);
        }
        return null;
    }

    public void deleteBill(Long billId) {
        billRepository.deleteById(billId);
    }
}