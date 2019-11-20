package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.AddCategoryRequest;
import com.categorise.transactions.fractal.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = BeanDefinitions.class)
class AddCategoryServiceTest {

  private static final String NEW_CATEGORY = "Supermarket Purchase";

  @Autowired private CategoriseTransactionsService service;

  @Test
  void shouldUpdateTransaction() throws Exception {
    String[] stringList = {"SAINSBURY'S"};
    AddCategoryRequest request =
        AddCategoryRequest.builder()
            .descriptionSearch(stringList)
            .newCategory(NEW_CATEGORY)
            .build();
    Transaction[] serviceMock =
        mapJsonFileToObject("responses/get-transactions-service-mock.json", Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    service.addCategory(request);
    List<Transaction> response = service.returnCurrentTransactionList();
    assertThat(response.get(2).getCategory()).isEqualTo(NEW_CATEGORY);
  }
}
