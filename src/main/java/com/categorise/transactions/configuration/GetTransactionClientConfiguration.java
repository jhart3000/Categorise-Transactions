package com.categorise.transactions.configuration;

import com.categorise.transactions.client.GetTransactionsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetTransactionClientConfiguration {
  @Bean
  GetTransactionsClient getTransactionsClient() {
    return new GetTransactionsClient();
  }
}
