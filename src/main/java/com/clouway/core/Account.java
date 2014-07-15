package com.clouway.core;

/**
 * Created by hisazzul@gmail.com on 7/11/14.
 */
public class Account {

    private double transactionAmount;
    private String deposit;
    private String withdraw;

    public String getDeposit() {
        return deposit;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}