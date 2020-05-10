package com.categorise.transactions.configuration;

import com.categorise.transactions.service.MongoDBInteractionsService;
import com.categorise.transactions.service.UpdateCategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCategoryServiceConfiguration {

  @Bean
  UpdateCategoryService updateCategoryService(
      MongoDBInteractionsService mongoDBInterationsService) {
    return new UpdateCategoryService(mongoDBInterationsService);
  }
}
