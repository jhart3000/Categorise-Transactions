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
}
