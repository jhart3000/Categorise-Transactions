package com.categorise.transactions.configuration;

import com.categorise.transactions.client.GetTransactionsClient;
import com.categorise.transactions.service.CategoriseTransactionsService;
import com.categorise.transactions.service.MongoDBInteractionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoriseTransactionsServiceConfiguration {

  @Bean
  CategoriseTransactionsService categoriseTransactionsService(
      GetTransactionsClient client, MongoDBInteractionsService mongoDBInterationsService) {
    return new CategoriseTransactionsService(client, mongoDBInterationsService);
  }
}
