package com.eldorado.El_Dorado.utils;

import com.eldorado.El_Dorado.domain.Account;

public class BillUtils {

    public static void takingMoneyFromAccountToPayBIll(Account account, Double bill){
        double currentBalance = account.getBalance();
        double newBalance = currentBalance - bill;
        account.setBalance(newBalance);
    }

    public static void takingFromRewardsToPayBill(Account account, Double rewards){
        double currentRewards = account.getRewards();
        double newRewards = currentRewards - rewards;
        account.setRewards((int) newRewards);
    }



}
