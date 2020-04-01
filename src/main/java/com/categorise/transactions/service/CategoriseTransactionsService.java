package com.categorise.transactions.service;

import com.categorise.transactions.client.GetTransactionsClient;
import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.AddCategoryRequest;
import com.categorise.transactions.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CategoriseTransactionsService {

  private static final String COFFEE_PURCHASE = "Coffee Purchase";
  private static final String AMAZON_PURCHASE = "Amazon Purchase";

  @Autowired private GetTransactionsClient client;

  private List<Transaction> transactionList;

  public CategoriseTransactionsService(List<Transaction> transactionList) {
    this.transactionList = transactionList;
  }

  public List<Transaction> categoriseTransactions() throws Exception {

    transactionList = client.getTransactionsHardCoded();
    transactionList.stream()
        .filter(this::isCoffeePurchase)
        .forEach(transaction -> transaction.setCategory(COFFEE_PURCHASE));
    transactionList.stream()
        .filter(this::isAmazonPurchase)
        .forEach(transaction -> transaction.setCategory(AMAZON_PURCHASE));
    transactionList.stream()
        .filter(this::isNotCategorised)
        .forEach(transaction -> transaction.setCategory("Not Categorised"));

    return transactionList;
  }

  public List<Transaction> getTransactionsWithSameCategory(String category)
      throws ApplicationException {
    transactionListNullCheck();

    List<Transaction> sameCategoryList =
        transactionList.stream()
            .filter(transaction -> category.equalsIgnoreCase(transaction.getCategory()))
            .collect(Collectors.toList());

    if (sameCategoryList.isEmpty()) {
      throw new ApplicationException("Category Does Not Exist");
    }
    return sameCategoryList;
  }

  public void updateTransaction(String transactionId, String category) throws ApplicationException {
    transactionListNullCheck();

    if (transactionList.stream()
        .noneMatch(transaction -> transaction.getTransactionId().equals(transactionId))) {
      throw new ApplicationException("Transaction Id Does Not Exist");
    }

    transactionList.stream()
        .filter(transaction -> transaction.getTransactionId().equals(transactionId))
        .forEach(transaction -> transaction.setCategory(category));
  }

  public void addCategory(AddCategoryRequest request) throws ApplicationException {
    transactionListNullCheck();

    transactionList.stream()
        .filter(
            transaction ->
                Arrays.stream(request.getDescriptionSearch())
                    .parallel()
                    .anyMatch(transaction.getDescription()::contains))
        .forEach(transaction -> transaction.setCategory(request.getNewCategory()));
  }

  public List<Transaction> returnCurrentTransactionList() throws ApplicationException {
    transactionListNullCheck();
    return transactionList;
  }

  private void transactionListNullCheck() throws ApplicationException {
    if (transactionList.isEmpty()) {
      throw new ApplicationException("Categorise Transactions Api Must Be Called First");
    }
  }

  private boolean isNotCategorised(Transaction transaction) {
    return !AMAZON_PURCHASE.equalsIgnoreCase(transaction.getCategory())
        && !COFFEE_PURCHASE.equalsIgnoreCase(transaction.getCategory());
  }

  private boolean isAmazonPurchase(Transaction transaction) {
    return transaction.getDescription().toLowerCase().contains("amazon");
  }

  private boolean isCoffeePurchase(Transaction transaction) {
    return transaction.getDescription().toLowerCase().contains("coffee")
        || transaction.getDescription().toLowerCase().contains("starbucks")
        || transaction.getDescription().toLowerCase().contains("costa");
  }
}
