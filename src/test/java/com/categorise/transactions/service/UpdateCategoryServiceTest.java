package com.categorise.transactions.service;

import com.categorise.transactions.configuration.BeanDefinitions;
import com.categorise.transactions.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.helper.Constants.SERVICE_MOCK;
import static com.categorise.transactions.helper.Constants.UPDATED_CATEGORY;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = BeanDefinitions.class)
class UpdateCategoryServiceTest {

  @Autowired private CategoriseTransactionsService service;

  @Test
  void shouldUpdateTransaction() throws Exception {
    Transaction[] serviceMock = mapJsonFileToObject(SERVICE_MOCK, Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    service.updateTransaction("0ef942ea-d3ad-4f25-857b-4d4bb7f912d8", UPDATED_CATEGORY);
    List<Transaction> response = service.returnCurrentTransactionList();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo(UPDATED_CATEGORY);
  }

  @Test
  void shouldThrowNonExistentTransactionIdForUpdateTransaction() throws Exception {
    Transaction[] serviceMock = mapJsonFileToObject(SERVICE_MOCK, Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.updateTransaction("Invalid Id", UPDATED_CATEGORY));
    assertThat(errorResponse).hasMessage("Transaction Id Does Not Exist");
  }
}
