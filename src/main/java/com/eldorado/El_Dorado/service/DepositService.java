package com.eldorado.El_Dorado.service;


import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.domain.enums.TransactionType;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.DepositRepository;
import jakarta.transaction.TransactionRolledbackException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final AccountRepo accountRepo;

    @Autowired
    public DepositService(DepositRepository depositRepository, AccountRepo accountRepo){
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
    public Deposit makeRegularDeposit(Long accountId, Deposit deposit){
        Account account = verifyAccount(accountId);
        double accountBalance = account.getBalance();
        double newBalance = accountBalance + deposit.getAmount();
        account.setBalance(newBalance);
        accountRepo.save(account);
        return depositRepository.save(deposit);
    }

    @Transactional
    public Deposit makeP2PDeposit(Long accountId, Deposit deposit) throws TransactionRolledbackException {
        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = accountRepo.findById(deposit.getPayee_id()).orElse(null);
        if(payingAccount.getBalance() - deposit.getAmount() < 0){
            throw new TransactionRolledbackException("Insufficient funds");
        }
        if(receivingAccount == null){
            throw new ResourceNotFoundException("Receiving account not found");
        }
        double payerBalance = payingAccount.getBalance() - deposit.getAmount();
        double payeeBalance = receivingAccount.getBalance() + deposit.getAmount();
        payingAccount.setBalance(payerBalance);
        receivingAccount.setBalance(payeeBalance);
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
    protected Account verifyAccount(Long accountId){
        Account account = accountRepo.findById(accountId).orElse(null);
        if(account == null){
            throw new ResourceNotFoundException("Account not found");
        }
        return account;
    }

    /**
     * deleting deposit should reverse the process and result in a withdrawal
     * @param depositId
     * @return
     *
     * protected void verifyPoll(Long id) throws ResourceNotFoundException{
     *         Poll poll = pollRepository.findById(id).orElse(null);
     *         if(poll == null){
     *             throw new ResourceNotFoundException("Poll with id " + id + " not found");
     *         }
     *     }
     */
}
