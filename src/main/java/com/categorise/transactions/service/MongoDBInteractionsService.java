package com.categorise.transactions.service;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.mongodb.TransactionRepository;

import java.util.List;
import java.util.Objects;

public class MongoDBInteractionsService {

  private TransactionRepository transactionRepository;

  public MongoDBInteractionsService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public List<TransactionDocument> getAllFromMongoDB() throws ApplicationException {
    List<TransactionDocument> transactionDocumentList =
        transactionRepository.findAll().collectList().block();
    if (Objects.requireNonNull(transactionDocumentList).isEmpty()) {
      throw new ApplicationException(
          "Categorise Transactions Api Must Be Called First With useCach Set To False");
    } else {
      return transactionDocumentList;
    }
  }

  void saveAllToMongoDB(List<TransactionDocument> transactionList) {
    transactionRepository.saveAll(transactionList).subscribe();
  }
}
