package com.categorise.transactions.service;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.mongodb.TransactionDocument;

import java.util.List;

public class UpdateCategoryService {

  private MongoDBInteractionsService mongoDBInterationsService;

  public UpdateCategoryService(MongoDBInteractionsService mongoDBInterationsService) {
    this.mongoDBInterationsService = mongoDBInterationsService;
  }

  public MessageResponse updateTransaction(String transactionId, String category)
      throws ApplicationException {

    List<TransactionDocument> transactionList = mongoDBInterationsService.getAllFromMongoDB();

    if (transactionList.stream()
        .noneMatch(transaction -> transaction.getTransactionId().equals(transactionId))) {
      throw new ApplicationException("Transaction Id Does Not Exist");
    }

    transactionList.stream()
        .filter(transaction -> transaction.getTransactionId().equals(transactionId))
        .forEach(transaction -> transaction.setCategory(category));

    mongoDBInterationsService.saveAllToMongoDB(transactionList);

    return MessageResponse.builder().message("Transaction Category Updated").build();
  }
}
