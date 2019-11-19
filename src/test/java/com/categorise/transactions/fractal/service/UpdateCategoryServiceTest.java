package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = BeanDefinitions.class)
class UpdateCategoryServiceTest {

  @Autowired private CategoriseTransactionsService service;

  @Test
  void shouldUpdateTransaction() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("get-transactions-service-mock.json", Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    service.updateTransaction("0ef942ea-d3ad-4f25-857b-4d4bb7f912d8", "Updated Category");
    List<Transaction> response = service.returnCurrentTransactionList();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo("Updated Category");
  }

  @Test
  void shouldThrowNonExistentTransactionIdForUpdateTransaction() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("get-transactions-service-mock.json", Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.updateTransaction("Invalid Id", "Updated Category"));
    assertThat(errorResponse).hasMessage("Transaction Id Does Not Exist");
  }
}
