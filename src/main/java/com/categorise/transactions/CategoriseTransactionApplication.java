package com.categorise.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;

@SpringBootApplication(exclude = EmbeddedMongoAutoConfiguration.class)
public class CategoriseTransactionApplication {

  public static void main(String[] args) {
    SpringApplication.run(CategoriseTransactionApplication.class, args);
  }
}
