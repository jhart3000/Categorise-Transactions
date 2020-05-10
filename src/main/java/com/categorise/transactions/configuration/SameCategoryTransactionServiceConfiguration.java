package com.categorise.transactions.configuration;

import com.categorise.transactions.service.MongoDBInteractionsService;
import com.categorise.transactions.service.SameCategoryTransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SameCategoryTransactionServiceConfiguration {

  @Bean
  SameCategoryTransactionService sameCategoryTransactionService(
      MongoDBInteractionsService mongoDBInterationsService) {
    return new SameCategoryTransactionService(mongoDBInterationsService);
  }
}
