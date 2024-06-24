package com.eldorado.El_Dorado.service;


import com.eldorado.El_Dorado.domain.Account;
import com.eldorado.El_Dorado.domain.Deposit;
import com.eldorado.El_Dorado.domain.enums.Medium;
import com.eldorado.El_Dorado.domain.enums.Status;
import com.eldorado.El_Dorado.domain.enums.TransactionType;
import com.eldorado.El_Dorado.exception.ResourceNotFoundException;
import com.eldorado.El_Dorado.exception.TransactionFailedException;
import com.eldorado.El_Dorado.repository.AccountRepo;
import com.eldorado.El_Dorado.repository.DepositRepository;
import com.eldorado.El_Dorado.utils.DepositUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public Deposit makeDeposit(Long accountId, Deposit deposit) throws TransactionFailedException{
        TransactionType type = checkDepositType(deposit);
        Deposit newDeposit = null;
        if (type == TransactionType.DEPOSIT && accountId.equals(deposit.getPayee().getId())){
            newDeposit = makeRegularDeposit(accountId, deposit);
        } else if(type == TransactionType.P2P && !accountId.equals(deposit.getPayee().getId())){
            newDeposit =makeP2PDeposit(accountId, deposit);
        }
        return newDeposit;
    }

    @Transactional
    public Deposit updateDeposit(Long depositId, Deposit depositRequest, Long accountId) throws TransactionFailedException {
        Deposit deposit = verifyDeposit(depositId);
        Deposit newDeposit = new Deposit();
        if(deposit.getType() == TransactionType.DEPOSIT) {
            newDeposit.setType(deposit.getType());
            newDeposit.getPayee().setId(deposit.getPayee().getId());
            newDeposit.setDescription("Deposit update: REF Deposit #" + deposit.getId());
            newDeposit.setMedium(deposit.getMedium());
            newDeposit.setStatus(Status.PENDING);
            if (depositRequest.getAmount() == deposit.getAmount()) {
                newDeposit.setAmount(depositRequest.getAmount());
            } else {
                throw new TransactionFailedException("Deposit amount must equal amount in previous deposit.");
            }
            deleteDeposit(depositId, accountId);
            return makeDeposit(accountId, newDeposit);
        }else
            throw new TransactionFailedException("Unable to update");
    }


    @Transactional
    public Deposit deleteDeposit(Long depositId, Long accountId){
        Deposit oldDeposit = verifyDeposit(depositId);

        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = verifyAccount(oldDeposit.getPayee().getId());
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
    public Deposit makeRegularDeposit(Long accountId, Deposit deposit){
        Deposit newDeposit = null;
        if (checkDepositMedium(deposit) == Medium.Balance)
            newDeposit = makeRegularDepositUsingBalance(accountId, deposit);
        else if (checkDepositMedium(deposit) == Medium.Rewards)
            newDeposit = makeRegularDepositUsingRewards(accountId, deposit);

        newDeposit.setStatus(Status.PENDING);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        newDeposit.setStatus(Status.COMPLETED);
        return newDeposit;
    }

    @Transactional
    public Deposit makeP2PDeposit(Long accountId, Deposit deposit) throws TransactionFailedException {
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

    public Deposit makeP2PDepositUsingBalance(Long accountId, Deposit deposit) throws TransactionFailedException {
        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = accountRepo.findById(deposit.getPayee().getId()).orElse(null);
        assert receivingAccount != null;
        DepositUtils.depositP2PUsingBalance(payingAccount, receivingAccount, deposit.getAmount());
        accountRepo.save(payingAccount);
        accountRepo.save(receivingAccount);
        return depositRepository.save(deposit);
    }

    public Deposit makeP2PDepositUsingRewards(Long accountId, Deposit deposit) throws TransactionFailedException {
        Account payingAccount = verifyAccount(accountId);
        Account receivingAccount = accountRepo.findById(deposit.getPayee().getId()).orElse(null);
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
