package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.client.GetTransactionsClient;
import com.categorise.transactions.fractal.exception.ApplicationException;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CategoriseTransactionsService {

  @Autowired private GetTransactionsClient client;

  private List<Transaction> transactionList;

  public CategoriseTransactionsService(List<Transaction> transactionList) {
    this.transactionList = transactionList;
  }

  public List<Transaction> categoriseTransactions(CategoriseTransactionsRequest request)
      throws ApplicationException {

    transactionList = client.getTransactions(request);
    transactionList.stream()
        .forEach(
            transaction -> {
              if (transaction.getDescription().toLowerCase().contains("coffee")
                  || transaction.getDescription().toLowerCase().contains("starbuck")
                  || transaction.getDescription().toLowerCase().contains("costa")) {
                transaction.setCategory("Coffee Purchase");
              } else if (transaction.getDescription().toLowerCase().contains("amazon")) {
                transaction.setCategory("Amazon Purchase");
              } else {
                transaction.setCategory("Not Categorised");
              }
            });
    return transactionList;
  }

  public List<Transaction> getTransactionsWithSameCategory(String category)
      throws ApplicationException {

    transactionListNullCheck();

    List<Transaction> sameCategoryList = new ArrayList<>();

    transactionList.stream()
        .forEach(
            transaction -> {
              if (transaction.getCategory().equalsIgnoreCase(category)) {
                sameCategoryList.add(transaction);
              }
            });

    if (sameCategoryList.isEmpty()) {
      throw new ApplicationException("Category Does Not Exist");
    }

    return sameCategoryList;
  }

  private void transactionListNullCheck() throws ApplicationException {
    if (transactionList.isEmpty()) {
      throw new ApplicationException("Categorise Transactions Api Must Be Called First");
    }
  }
}
