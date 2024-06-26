package com.eldorado.El_Dorado.service;

import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Bill;
import com.eldorado.El_Dorado.domain.enums.Status;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.exception.TransactionFailedException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.BillRepository;
import com.eldorado.El_Dorado.utils.BillUtils;
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

    @Autowired
    private DepositService depositService;

    @Autowired
    private AccountRepo accountRepo;

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

    public Bill updateBill(Long billId, Bill updatedBill,Long accountId) {
        return billRepository.findById(billId).map(bill -> {
            bill.setNickName(updatedBill.getNickName());
            bill.setPayment_amount(updatedBill.getPayment_amount());
            bill.setBillStatus(updatedBill.getBillStatus());
            bill.setBillPayee(updatedBill.getBillPayee());
            bill.setPayment_date(updatedBill.getPayment_date());
            bill.setRecurring_date(updatedBill.getRecurring_date());
            bill.setUpcoming_payment_date(updatedBill.getUpcoming_payment_date());

            if(updatedBill.getBillStatus() == Status.PENDING){
                payBill(accountId,updatedBill);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                bill.setBillStatus(Status.COMPLETED);
            }
            if (updatedBill.getBillStatus() == Status.COMPLETED){
                try {
                    throw new TransactionFailedException("Bill already paid for!");
                } catch (TransactionFailedException transaction) {
                    throw new RuntimeException(transaction);
                }
            }
            if (updatedBill.getBillStatus() == Status.RECURRING){
                if(bill.getRecurring_date() == null){
                    try {
                        throw new ResourceNotFoundException("Need a date!");
                    } catch (ResourceNotFoundException Resource) {
                        throw new RuntimeException(Resource);
                    }
                }
            }
            return billRepository.save(bill);
        }).orElseThrow(() -> new ResourceNotFoundException("Bill Id " + billId + " doesn't exist."));
    }

    public List<Bill> getAllBillsByCustomer(Long customerId) {
        return billRepository.findAll();
    }

    public Bill payBill(Long accountId, Bill bill){

        if (bill.getBillStatus() == Status.COMPLETED){
            throw new ResourceNotFoundException("Bill is already paid for");
        }
        else if (bill.getBillStatus() == Status.CANCELLED){
            throw new ResourceNotFoundException("Bill has been cancelled");
        }
        else if (bill.getBillStatus() == Status.PENDING){
            makePaymentForBillWithBalance(accountId,bill);
        }

        return billRepository.save(bill);
    }

    protected Bill verify(Long billId){
        if(!billRepository.existsById(billId)){
            throw new ResourceNotFoundException("Bill id: " + billId + " cannot be found");
        }
        return billRepository.findById(billId).orElse(null);
    }

    public Bill makePaymentForBillWithBalance(Long accountId,Bill bill){
        Account account = depositService.verifyAccount(accountId);
        BillUtils.takingMoneyFromAccountToPayBIll(account, bill.getPayment_amount());
        accountRepo.save(account);
        return billRepository.save(bill);
    }

    public Bill makePaymentForBillWithRewards(Long accountId,Bill bill){
        Account account = depositService.verifyAccount(accountId);
        BillUtils.takingFromRewardsToPayBill(account,bill.getPayment_amount());
        accountRepo.save(account);
        return billRepository.save(bill);
    }
}
