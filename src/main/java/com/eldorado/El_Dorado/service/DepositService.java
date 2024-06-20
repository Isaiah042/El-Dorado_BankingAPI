package com.eldorado.El_Dorado.service;


import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.domain.enums.Medium;
import com.eldorado.El_Dorado.domain.enums.TransactionType;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.DepositRepository;
import com.eldorado.El_Dorado.utils.DepositUtils;
import jakarta.transaction.TransactionRolledbackException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final AccountRepo accountRepo;

    @Autowired
    public DepositService(DepositRepository depositRepository, AccountRepo accountRepo) {
        this.depositRepository = depositRepository;
        this.accountRepo = accountRepo;
    }

    public Iterable<Deposit> getAllDeposits(Long accountId){
        return depositRepository.findByAccount(accountId);
    }

    public Deposit getById(Long depositId){
        verifyDeposit(depositId);
        return depositRepository.findById(depositId).orElse(null);
    }

    @Transactional
    public Deposit makeDeposit(Long accountId, Deposit deposit) throws TransactionRolledbackException{
        TransactionType type = checkDepositType(deposit);
        Deposit newDeposit = null;
        if (type == TransactionType.DEPOSIT){
            newDeposit = makeRegularDeposit(accountId, deposit);
        } else if(type == TransactionType.P2P){
            newDeposit =makeP2PDeposit(accountId, deposit);
        }
        return newDeposit;
    }

    public ResponseEntity<?> updateDeposit(Long depositId, Deposit depositRequest){
        verifyDeposit(depositId);
        return depositRepository.findById(depositId).map(deposit ->{
            deposit.setType(depositRequest.getType());
            deposit.setTransaction_date(LocalDateTime.now());
            deposit.setStatus(depositRequest.getStatus());
            deposit.setPayee_id(depositRequest.getPayee_id());
            deposit.setMedium(depositRequest.getMedium());
            deposit.setAmount(depositRequest.getAmount());
            deposit.setDescription(depositRequest.getDescription());
            depositRepository.save(deposit);
            return new ResponseEntity(deposit, HttpStatus.ACCEPTED);
        }).orElseThrow(() -> new ResourceNotFoundException("Deposit ID does not exist."));
    }


    public ResponseEntity<?> deleteDeposit(Long depositId){
        Deposit depositToDelete = depositRepository.findById(depositId).orElse(null);
        if(depositToDelete == null){
            throw new ResourceNotFoundException("This id does not exist in deposits");
        }else
            //perform the withdrawal here
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Transactional
    public Deposit makeRegularDeposit(Long accountId, Deposit deposit) throws TransactionRolledbackException{
        Deposit newDeposit = null;
        if (checkDepositMedium(deposit) == Medium.Balance)
            newDeposit = makeRegularDepositUsingBalance(accountId, deposit);
        else if (checkDepositMedium(deposit) == Medium.Rewards)
            newDeposit = makeRegularDepositUsingRewards(accountId, deposit);

        return newDeposit;
    }

    @Transactional
    public Deposit makeP2PDeposit(Long accountId, Deposit deposit) throws TransactionRolledbackException {
        Deposit newDeposit = null;
        if (checkDepositMedium(deposit) == Medium.Balance)
            newDeposit = makeP2PDepositUsingBalance(accountId, deposit);
        else if (checkDepositMedium(deposit) == Medium.Rewards)
            newDeposit = makeP2PDepositUsingRewards(accountId, deposit);

        return newDeposit;
    }


    public Deposit makeRegularDepositUsingBalance(Long accountId, Deposit deposit){
        Account account = verifyAccount(accountId);
        DepositUtils.depositUsingBalance(account, deposit.getAmount());
        accountRepo.save(account);
        return depositRepository.save(deposit);
    }


    public Deposit makeRegularDepositUsingRewards(Long accountId, Deposit deposit){
        Account account = verifyAccount(accountId);
        DepositUtils.depositUsingRewards(account, deposit.getAmount());
        accountRepo.save(account);
        return depositRepository.save(deposit);
    }

    public Deposit makeP2PDepositUsingBalance(Long accountId, Deposit deposit) throws TransactionRolledbackException {
        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = accountRepo.findById(deposit.getPayee_id()).orElse(null);
        assert receivingAccount != null;
        DepositUtils.depositP2PUsingBalance(payingAccount, receivingAccount, deposit.getAmount());
        accountRepo.save(payingAccount);
        accountRepo.save(receivingAccount);
        return depositRepository.save(deposit);
    }

    public Deposit makeP2PDepositUsingRewards(Long accountId, Deposit deposit) throws TransactionRolledbackException {
        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = accountRepo.findById(deposit.getPayee_id()).orElse(null);
        assert receivingAccount != null;
        DepositUtils.depositP2PUsingRewards(payingAccount, receivingAccount, deposit.getAmount());
        accountRepo.save(payingAccount);
        accountRepo.save(receivingAccount);
        return depositRepository.save(deposit);
    }

    protected void verifyDeposit(Long depositId){
        Deposit deposit = depositRepository.findById(depositId).orElse(null);
        if(deposit == null){
            throw new ResourceNotFoundException("Deposit with id " + depositId + " not found");
        }
    }

    protected TransactionType checkDepositType(Deposit deposit){
        return deposit.getType();
    }

    protected Medium checkDepositMedium(Deposit deposit){
        return deposit.getMedium();
    }
    protected Account verifyAccount(Long accountId){
        if(!accountRepo.existsById(accountId)){
            throw new ResourceNotFoundException("Account not found");
        }
        return accountRepo.findById(accountId).orElse(null);
    }

}
