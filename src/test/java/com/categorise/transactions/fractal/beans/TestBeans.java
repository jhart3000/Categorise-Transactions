package com.categorise.transactions.fractal.beans;

import com.categorise.transactions.fractal.client.GetTransactionsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestBeans {

  @Bean
  GetTransactionsClient getTransactionsClient() {
    return new GetTransactionsClient();
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
