package com.eldorado.El_Dorado.service;


import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.domain.enums.Medium;
import com.eldorado.El_Dorado.domain.enums.Status;
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


    @Transactional
    public Deposit deleteDeposit(Long depositId, Long accountId) throws TransactionRolledbackException {
        Deposit oldDeposit = verifyDeposit(depositId);

        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = verifyAccount(oldDeposit.getPayee_id());
        double amount = oldDeposit.getAmount();
        if(checkDepositMedium(oldDeposit) == Medium.Balance){
            payingAccount.setBalance(payingAccount.getBalance() + amount);
            receivingAccount.setBalance(receivingAccount.getBalance() - amount);
        } else if (checkDepositMedium(oldDeposit) == Medium.Rewards){
            payingAccount.setRewards((int) (payingAccount.getRewards() + amount));
        }

        oldDeposit.setAmount(0.0);
        oldDeposit.setStatus(Status.CANCELLED);
        oldDeposit.setDescription("Reversal");
        return depositRepository.save(oldDeposit);
    }

    @Transactional
    public Deposit makeRegularDeposit(Long accountId, Deposit deposit) throws TransactionRolledbackException{
        Deposit newDeposit = null;
        if (checkDepositMedium(deposit) == Medium.Balance)
            newDeposit = makeRegularDepositUsingBalance(accountId, deposit);
        else if (checkDepositMedium(deposit) == Medium.Rewards)
            newDeposit = makeRegularDepositUsingRewards(accountId, deposit);

        newDeposit.setStatus(Status.PENDING);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        newDeposit.setStatus(Status.COMPLETED);
        return newDeposit;
    }

    @Transactional
    public Deposit makeP2PDeposit(Long accountId, Deposit deposit) throws TransactionRolledbackException {
        Deposit newDeposit = null;
        if (checkDepositMedium(deposit) == Medium.Balance)
            newDeposit = makeP2PDepositUsingBalance(accountId, deposit);
        else if (checkDepositMedium(deposit) == Medium.Rewards)
            newDeposit = makeP2PDepositUsingRewards(accountId, deposit);

        newDeposit.setStatus(Status.PENDING);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        newDeposit.setStatus(Status.COMPLETED);
        return newDeposit;
    }


    public Deposit makeRegularDepositUsingBalance(Long accountId, Deposit deposit) throws TransactionRolledbackException{
        Account account = verifyAccount(accountId);
        DepositUtils.depositUsingBalance(account, deposit.getAmount());
        accountRepo.save(account);
        return depositRepository.save(deposit);
    }


    public Deposit makeRegularDepositUsingRewards(Long accountId, Deposit deposit) throws TransactionRolledbackException{
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

    protected Deposit verifyDeposit(Long depositId){
        if(!depositRepository.existsById(depositId)){
            throw new ResourceNotFoundException("Deposit with id " + depositId + " not found");
        }
        return depositRepository.findById(depositId).orElse(null);
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
