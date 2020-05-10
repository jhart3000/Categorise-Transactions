package com.categorise.transactions.configuration;

import com.categorise.transactions.mongodb.TransactionRepository;
import com.categorise.transactions.service.MongoDBInteractionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBInteractionsServiceConfiguration {
  @Bean
  MongoDBInteractionsService mongoDBInterationsService(
      TransactionRepository transactionRepository) {
    return new MongoDBInteractionsService(transactionRepository);
  }
}
