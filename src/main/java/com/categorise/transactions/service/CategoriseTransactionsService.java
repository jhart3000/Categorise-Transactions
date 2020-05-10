package com.categorise.transactions.service;

import com.categorise.transactions.client.GetTransactionsClient;
import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.mongodb.TransactionRepository;

import java.util.List;

public class CategoriseTransactionsService {

  private static final String COFFEE_PURCHASE = "Coffee Purchase";
  private static final String AMAZON_PURCHASE = "Amazon Purchase";

  private GetTransactionsClient client;
  private TransactionRepository transactionRepository;
  private MongoDBInteractionsService mongoDBInteractionsService;

  public CategoriseTransactionsService(
      GetTransactionsClient client,
      TransactionRepository transactionRepository,
      MongoDBInteractionsService mongoDBInterationsService) {
    this.client = client;
    this.transactionRepository = transactionRepository;
    this.mongoDBInteractionsService = mongoDBInterationsService;
  }

  public List<TransactionDocument> categoriseTransactions(boolean useCache) throws Exception {

    List<TransactionDocument> transactionList;
    if (useCache) {
      transactionList = mongoDBInteractionsService.getAllFromMongoDB();
    } else {
      transactionList = client.getTransactionsHardCoded();
    }
    transactionList.stream()
        .filter(this::isCoffeePurchase)
        .forEach(transaction -> transaction.setCategory(COFFEE_PURCHASE));
    transactionList.stream()
        .filter(this::isAmazonPurchase)
        .forEach(transaction -> transaction.setCategory(AMAZON_PURCHASE));
    transactionList.stream()
        .filter(this::isNotCategorised)
        .forEach(transaction -> transaction.setCategory("Not Categorised"));

    mongoDBInteractionsService.saveAllToMongoDB(transactionList);
    return transactionList;
  }

  private boolean isNotCategorised(TransactionDocument transaction) {
    return !AMAZON_PURCHASE.equalsIgnoreCase(transaction.getCategory())
        && !COFFEE_PURCHASE.equalsIgnoreCase(transaction.getCategory());
  }

  private boolean isAmazonPurchase(TransactionDocument transaction) {
    return transaction.getDescription().toLowerCase().contains("amazon");
  }

  private boolean isCoffeePurchase(TransactionDocument transaction) {
    return transaction.getDescription().toLowerCase().contains("coffee")
        || transaction.getDescription().toLowerCase().contains("starbucks")
        || transaction.getDescription().toLowerCase().contains("costa");
  }
}
