package com.clouway.bank.core;

/**
 * Created by Stanislav Valov <hisazzul@gmail.com>
 */
public class Account {

  private String transactionAmount;

  public Account(String transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  public String getTransactionAmount() {
    return transactionAmount;
  }
}