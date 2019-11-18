package com.categorise.transactions.fractal.configuration;

import com.categorise.transactions.fractal.client.GetTransactionsClient;
import com.categorise.transactions.fractal.model.Transaction;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class BeanDefinitions {

  @Bean
  GetTransactionsClient getTransactionsClient() {
    return new GetTransactionsClient();
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  CategoriseTransactionsService categoriseTransactionsService(List<Transaction> transactionList) {
    return new CategoriseTransactionsService(transactionList);
  }
}
