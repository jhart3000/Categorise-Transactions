package com.categorise.transactions.service;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.AddCategoryRequest;
import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.mongodb.TransactionDocument;

import java.util.Arrays;
import java.util.List;

public class AddCategoryService {

  private MongoDBInteractionsService mongoDBInterationsService;

  public AddCategoryService(MongoDBInteractionsService mongoDBInterationsService) {
    this.mongoDBInterationsService = mongoDBInterationsService;
  }

  public MessageResponse addCategory(AddCategoryRequest request) throws ApplicationException {
    List<TransactionDocument> transactionList = mongoDBInterationsService.getAllFromMongoDB();

    transactionList.stream()
        .filter(
            transaction ->
                Arrays.stream(request.getDescriptionSearch())
                    .parallel()
                    .anyMatch(transaction.getDescription()::contains))
        .forEach(transaction -> transaction.setCategory(request.getNewCategory()));

    mongoDBInterationsService.saveAllToMongoDB(transactionList);

    return MessageResponse.builder().message("Category Successfully Added").build();
  }
}
