package com.categorise.transactions.configuration;

import com.categorise.transactions.service.AddCategoryService;
import com.categorise.transactions.service.MongoDBInteractionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AddCategoryServiceConfiguration {

  @Bean
  AddCategoryService addCategoryService(MongoDBInteractionsService mongoDBInterationsService) {
    return new AddCategoryService(mongoDBInterationsService);
  }
}
