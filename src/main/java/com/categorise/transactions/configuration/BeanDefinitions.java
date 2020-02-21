package com.categorise.transactions.configuration;

import com.categorise.transactions.client.GetTransactionsClient;
import com.categorise.transactions.model.Transaction;
import com.categorise.transactions.service.CategoriseTransactionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BeanDefinitions {

  @Bean
  GetTransactionsClient getTransactionsClient() {
    return new GetTransactionsClient();
  }

  @Bean
  CategoriseTransactionsService categoriseTransactionsService(List<Transaction> transactionList) {
    return new CategoriseTransactionsService(transactionList);
  }
}
