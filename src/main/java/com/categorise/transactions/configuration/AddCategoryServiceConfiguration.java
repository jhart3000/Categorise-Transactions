package com.categorise.transactions.configuration;

import com.categorise.transactions.mongodb.TransactionRepository;
import com.categorise.transactions.service.AddCategoryService;
import com.categorise.transactions.service.MongoDBInteractionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AddCategoryServiceConfiguration {

  @Bean
  AddCategoryService addCategoryService(
      TransactionRepository transactionRepository,
      MongoDBInteractionsService mongoDBInterationsService) {
    return new AddCategoryService(transactionRepository, mongoDBInterationsService);
  }
}
