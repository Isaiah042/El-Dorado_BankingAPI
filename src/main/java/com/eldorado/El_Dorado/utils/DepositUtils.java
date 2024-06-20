package com.eldorado.El_Dorado.utils;

import com.eldorado.El_Dorado.domain.Account;
import jakarta.transaction.TransactionRolledbackException;

public class DepositUtils {

    public static void depositUsingBalance(Account account, Double amount){
        double currentBalance = account.getBalance();
        double newBalance = currentBalance + amount;
        account.setBalance(newBalance);

    }

    public static void depositUsingRewards(Account account, Double amount){
        double currentBalance = account.getRewards();
        double newBalance = currentBalance + amount;
        account.setRewards((int) newBalance);
    }

    public static void depositP2PUsingRewards(Account payingAccount, Account receivingAccount, Double amount) throws TransactionRolledbackException{
        double payerBalance = payingAccount.getRewards();
        double payeeBalance = receivingAccount.getRewards();
        if(payerBalance - amount > 0) {
            payeeBalance = payeeBalance + amount;
            receivingAccount.setRewards((int) payeeBalance);
            payerBalance = payerBalance - amount;
            payingAccount.setRewards((int) payerBalance);
        } else
            throw new TransactionRolledbackException("Insufficient funds!");
    }

    public static void depositP2PUsingBalance(Account payingAccount, Account receivingAccount, Double amount) throws TransactionRolledbackException{
        double payerBalance = payingAccount.getBalance();
        double payeeBalance = receivingAccount.getBalance();
        if(payerBalance - amount > 0) {
            payeeBalance = payeeBalance + amount;
            receivingAccount.setBalance(payeeBalance);
            payerBalance = payerBalance - amount;
            payingAccount.setBalance(payerBalance);
        }
        else
            throw new TransactionRolledbackException("Insufficient funds!");
    }

    public static void reverseDeposit(){}

}
