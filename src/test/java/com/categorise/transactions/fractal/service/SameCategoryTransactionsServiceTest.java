package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.fractal.helper.Constants.COFFEE_PURCHASE;
import static com.categorise.transactions.fractal.helper.Constants.SERVICE_MOCK;
import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = BeanDefinitions.class)
class SameCategoryTransactionsServiceTest {

  @Autowired private CategoriseTransactionsService service;

  @Test
  void shouldReturnTransactionWithTheSameCategory() throws Exception {
    Transaction[] serviceMock = mapJsonFileToObject(SERVICE_MOCK, Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    List<Transaction> response = service.getTransactionsWithSameCategory(COFFEE_PURCHASE);
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(3);
  }

  @Test
  void shouldThrowNullTransactionListForTransactionWithTheSameCategory() {
    Throwable errorResponse =
        catchThrowable(() -> service.getTransactionsWithSameCategory(COFFEE_PURCHASE));
    assertThat(errorResponse).hasMessage("Categorise Transactions Api Must Be Called First");
  }

  @Test
  void shouldThrowCategoryDoesNotExistForTransactionWithTheSameCategory() throws Exception {
    Transaction[] serviceMock = mapJsonFileToObject(SERVICE_MOCK, Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.getTransactionsWithSameCategory("Unknown Category"));
    assertThat(errorResponse).hasMessage("Category Does Not Exist");
  }
}
