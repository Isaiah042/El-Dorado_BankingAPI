package com.eldorado.El_Dorado.service;
import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.DepositRepository;
import com.eldorado.El_Dorado.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveWithdrawal(Withdrawal withdrawal) {
        withdrawalRepository.save(withdrawal);
    }

    public Iterable<Withdrawal> getAllWithdrawals() {
        return (List<Withdrawal>) withdrawalRepository.findAll();
    }

    public Optional<Withdrawal> getWithdrawalById(Long id) {
        verifyWithdrawal(id);
        return withdrawalRepository.findById(id);
    }

    public void updateWithdrawal(Long id, Withdrawal withdrawal) {
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
        }

    }

    public void deleteWithdrawalById(Long id) {
        verifyWithdrawal(id);

        withdrawalRepository.deleteById(id);
    }

    public void verifyWithdrawal(Long withdrawalId) throws ResourceNotFoundException {
        Withdrawal w = withdrawalRepository.findById(withdrawalId).orElse(null);
        if (w == null){
            throw new ResourceNotFoundException("Withdrawal with id " + withdrawalId + " not found");
        }
    }

}
