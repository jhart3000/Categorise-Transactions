package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.client.GetTransactionsClient;
import com.categorise.transactions.fractal.exception.ClientException;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoriseTransactionsService {

  @Autowired private GetTransactionsClient client;

  private List<Transaction> transactionList;

  public List<Transaction> categoriseTransactions(CategoriseTransactionsRequest request)
      throws ClientException {

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
}
