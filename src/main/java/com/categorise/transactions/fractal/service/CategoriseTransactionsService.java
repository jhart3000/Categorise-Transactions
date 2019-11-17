package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.client.GetTransactionsClient;
import com.categorise.transactions.fractal.exception.ClientException;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriseTransactionsService {

  @Autowired private GetTransactionsClient client;

  private List<Transaction> transactionList;

  public List<Transaction> categoriseTransactions(CategoriseTransactionsRequest request)
      throws ClientException {
    transactionList = client.getTransactions(request);
    return transactionList;
  }
}
