package com.categorise.transactions.service;

import com.categorise.transactions.client.GetTransactionsClient;
import com.categorise.transactions.configuration.BeanDefinitions;
import com.categorise.transactions.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.helper.Constants.SERVICE_MOCK;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = BeanDefinitions.class)
class CategoriseTransactionsServiceTest {

  @MockBean private GetTransactionsClient client;

  @Autowired private CategoriseTransactionsService service;

  @Test
  void shouldReturnCategorisedTransactionList() throws Exception {
    Transaction[] clientMock = mapJsonFileToObject(SERVICE_MOCK, Transaction[].class);
    given(client.getTransactionsHardCoded()).willReturn(Arrays.asList(clientMock));
    List<Transaction> response = service.categoriseTransactions();
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo("Coffee Purchase");
  }
}
