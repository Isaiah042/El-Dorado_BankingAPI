package com.eldorado.El_Dorado.service;
import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.domain.enums.Status;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.exception.TransactionFailedException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.DepositRepository;
import com.eldorado.El_Dorado.repository.WithdrawalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private AccountRepo accountRepository;

    public Withdrawal makeWithdrawal(long accountId, Withdrawal withdrawal) {
        Account payer = accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));
        withdrawal.setPayer(payer);
        double currentBalance = payer.getBalance();
        double withdrawalAmount = withdrawal.getAmount();
        if(withdrawal.getStatus() == Status.COMPLETED) {

            if (currentBalance < withdrawalAmount) {
                try {
                    throw new TransactionFailedException("Insufficient funds for payer with id " + accountId);
                } catch (TransactionFailedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(currentBalance > withdrawalAmount){
                payer.setBalance(currentBalance - withdrawalAmount);
            }
        }
        withdrawal.setPayer(payer);
        return withdrawalRepository.save(withdrawal);
    }



    public Iterable<Withdrawal> getAllWithdrawals() {
        return withdrawalRepository.findAll();
    }

    public Optional<Withdrawal> getWithdrawalById(Long id) {
        verifyWithdrawal(id);
        return withdrawalRepository.findById(id);
    }

    public ResponseEntity<?> updateWithdrawal(Long id, Withdrawal withdrawal) {
        verifyWithdrawal(id);
        Optional<Withdrawal> existingWithdrawal = withdrawalRepository.findById(id);
        if (existingWithdrawal.isPresent()) {
            Withdrawal updatedWithdrawal = existingWithdrawal.get();
            updatedWithdrawal.setType(withdrawal.getType());
            updatedWithdrawal.setStatus(withdrawal.getStatus());
            updatedWithdrawal.setMedium(withdrawal.getMedium());
            updatedWithdrawal.setAmount(withdrawal.getAmount());
            updatedWithdrawal.setDescription(withdrawal.getDescription());
            Withdrawal savedWithdrawal = withdrawalRepository.save(updatedWithdrawal);
            if (savedWithdrawal.getStatus() == Status.COMPLETED) {
                Account account = savedWithdrawal.getPayer();
                double currentBalance = account.getBalance();
                double withdrawalAmount = savedWithdrawal.getAmount();

                if (currentBalance < withdrawalAmount) {
                    try {
                        throw new TransactionFailedException("Insufficient funds for payer");
                    } catch (TransactionFailedException e) {
                        throw new RuntimeException(e);
                    }
                }
                account.setBalance(currentBalance - withdrawalAmount);
                accountRepository.save(account);
            }

            return ResponseEntity.ok(savedWithdrawal);
        }
        return ResponseEntity.notFound().build();
    }


    public void verifyWithdrawal(Long withdrawalId) throws ResourceNotFoundException {
        Withdrawal w = withdrawalRepository.findById(withdrawalId).orElse(null);
        if (w == null) {
            throw new ResourceNotFoundException("Withdrawal with id " + withdrawalId + " not found");
        }
    }
}
