package com.categorise.transactions.service;

import com.categorise.transactions.client.GetTransactionsClient;
import com.categorise.transactions.mongodb.TransactionDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.helper.Constants.SERVICE_MOCK;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoriseTransactionsServiceTest {

  private CategoriseTransactionsService service;

  @Mock private GetTransactionsClient client;
  @Mock private MongoDBInteractionsService mongoDBInterationsService;

  @BeforeEach
  void setUp() {
    service = new CategoriseTransactionsService(client, mongoDBInterationsService);
  }

  @Test
  void shouldReturnCategorisedTransactionListWhenUseCacheIsFalse() throws Exception {
    TransactionDocument[] clientMock =
        mapJsonFileToObject(SERVICE_MOCK, TransactionDocument[].class);
    given(client.getTransactionsHardCoded()).willReturn(Arrays.asList(clientMock));
    List<TransactionDocument> response = service.categoriseTransactions(false);
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo("Coffee Purchase");
  }

  @Test
  void shouldReturnCategorisedTransactionListWhenUseCacheIsTrue() throws Exception {
    TransactionDocument[] clientMock =
        mapJsonFileToObject(SERVICE_MOCK, TransactionDocument[].class);
    given(mongoDBInterationsService.getAllFromMongoDB()).willReturn(Arrays.asList(clientMock));
    List<TransactionDocument> response = service.categoriseTransactions(true);
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo("Coffee Purchase");
  }
}
