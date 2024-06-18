package com.eldorado.El_Dorado.service;
import com.eldorado.El_Dorado.domain.Withdrawal;
import com.eldorado.El_Dorado.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    //timestamp of current datetime as string on creation of a deposit
    /**
     * Get withdrawals for a specific account
     *
     * Get withdrawal by id
     *
     * POST Create a withdrawal
     *
     * PUT Update a specified existing withdrawal
     *
     * DELETE Delete a specific existing withdrawal
     */


    public Withdrawal saveWithdrawal(Withdrawal withdrawal) {
        return withdrawalRepository.save(withdrawal);
    }

    public List<Withdrawal> getAllWithdrawals() {
        return (List<Withdrawal>) withdrawalRepository.findAll();
    }

    public Optional<Withdrawal> getWithdrawalById(Long id) {
        return withdrawalRepository.findById(id);
    }

    public Withdrawal updateWithdrawal(Long id, Withdrawal withdrawal) {
        Optional<Withdrawal> existingWithdrawal = withdrawalRepository.findById(id);
        if (existingWithdrawal.isPresent()) {
            Withdrawal updatedWithdrawal = existingWithdrawal.get();
            updatedWithdrawal.setType(withdrawal.getType());
            updatedWithdrawal.setStatus(withdrawal.getStatus());
            updatedWithdrawal.setMedium(withdrawal.getMedium());
            updatedWithdrawal.setAmount(withdrawal.getAmount());
            updatedWithdrawal.setDescription(withdrawal.getDescription());
            return withdrawalRepository.save(updatedWithdrawal);
        }
        return null;
    }

    public void deleteWithdrawal(Long id) {
        withdrawalRepository.deleteById(id);
    }

}
