package com.categorise.transactions.fractal.service;

import com.categorise.transactions.fractal.client.GetTransactionsClient;
import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.fractal.helper.Constants.CLIENT_REQUEST;
import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = BeanDefinitions.class)
class CategoriseTransactionsServiceTest {

  @MockBean private GetTransactionsClient client;

  @Autowired private CategoriseTransactionsService service;

  @Test
  void shouldReturnCategorisedTransactionList() throws Exception {
    Transaction[] clientMock =
        mapJsonFileToObject("get-transactions-client-mock.json", Transaction[].class);
    given(client.getTransactions(CLIENT_REQUEST)).willReturn(Arrays.asList(clientMock));
    List<Transaction> response = service.categoriseTransactions(CLIENT_REQUEST);
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo("Coffee Purchase");
  }

  @Test
  void shouldReturnTransactionWithTheSameCategory() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("get-transactions-service-mock.json", Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    List<Transaction> response = service.getTransactionsWithSameCategory("Coffee Purchase");
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(3);
  }

  @Test
  void shouldThrowNullTransactionListForTransactionWithTheSameCategory() {
    Throwable errorResponse =
        catchThrowable(() -> service.getTransactionsWithSameCategory("Coffee Purchase"));
    assertThat(errorResponse).hasMessage("Categorise Transactions Api Must Be Called First");
  }

  @Test
  void shouldThrowCategoryDoesNotExistForTransactionWithTheSameCategory() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("get-transactions-service-mock.json", Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.getTransactionsWithSameCategory("Unknown Category"));
    assertThat(errorResponse).hasMessage("Category Does Not Exist");
  }

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
  void shouldThrowNonExistentTransationIdForUpdateTransaction() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("get-transactions-service-mock.json", Transaction[].class);
    service = new CategoriseTransactionsService(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.updateTransaction("Invalid Id", "Updated Category"));
    assertThat(errorResponse).hasMessage("Transaction Id Does Not Exist");
  }
}
