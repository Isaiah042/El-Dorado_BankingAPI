package com.eldorado.El_Dorado.service;
import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.exception.TransactionFailedException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.DepositRepository;
import com.eldorado.El_Dorado.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private AccountRepo accountRepository;

    public void saveWithdrawal(Withdrawal withdrawal) throws TransactionFailedException {
        Long accountId = withdrawal.getId();
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with id " + accountId + " not found"));

        double currentBalance = account.getBalance();
        double withdrawalAmount = withdrawal.getAmount();
        if (currentBalance < withdrawalAmount) {
            throw new TransactionFailedException("Insufficient funds for account with id " + accountId);
        }

        // Deduct the withdrawal amount from the account balance
        account.setBalance(currentBalance - withdrawalAmount);
        withdrawalRepository.save(withdrawal);
    }

    public ResponseEntity<?> getAllWithdrawals(Account account, Long accountId, Withdrawal withdrawal) {
        double balance = account.getBalance();
        return new ResponseEntity<>(withdrawalRepository.findAll(), HttpStatus.OK);
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
            withdrawalRepository.save(updatedWithdrawal);
            return new ResponseEntity(updatedWithdrawal, HttpStatus.ACCEPTED);
        }
        return null;
    }

//    public void deleteWithdrawalById(Long id) {
//        verifyWithdrawal(id);
//        withdrawalRepository.deleteById(id);
//    }

    public void verifyWithdrawal(Long withdrawalId) throws ResourceNotFoundException {
        Withdrawal w = withdrawalRepository.findById(withdrawalId).orElse(null);
        if (w == null){
            throw new ResourceNotFoundException("Withdrawal with id " + withdrawalId + " not found");
        }
    }
}
