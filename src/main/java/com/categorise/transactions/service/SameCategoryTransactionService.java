package com.categorise.transactions.service;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.mongodb.TransactionDocument;

import java.util.List;
import java.util.stream.Collectors;

public class SameCategoryTransactionService {

  private MongoDBInteractionsService mongoDBInterationsService;

  public SameCategoryTransactionService(MongoDBInteractionsService mongoDBInterationsService) {
    this.mongoDBInterationsService = mongoDBInterationsService;
  }

  public List<TransactionDocument> getTransactionsWithSameCategory(String category)
      throws ApplicationException {

    List<TransactionDocument> sameCategoryList =
        mongoDBInterationsService.getAllFromMongoDB().stream()
            .filter(transaction -> category.equalsIgnoreCase(transaction.getCategory()))
            .collect(Collectors.toList());

    if (sameCategoryList.isEmpty()) {
      throw new ApplicationException("Category Does Not Exist");
    }
    return sameCategoryList;
  }
}
