package com.categorise.transactions.service;

import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.mongodb.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.List;

import static com.categorise.transactions.helper.Constants.SERVICE_MOCK;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MongoDBInteractionServiceTest {

  private MongoDBInteractionsService service;
  @Mock private TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    service = new MongoDBInteractionsService(transactionRepository);
  }

  @Test
  void shouldThrowErrorWhenMongoDBReturnsEmptyList() {
    given(transactionRepository.findAll()).willReturn(Flux.empty());
    Throwable errorResponse = catchThrowable(() -> service.getAllFromMongoDB());
    assertThat(errorResponse)
        .hasMessage("Categorise Transactions Api Must Be Called First With useCach Set To False");
  }

  @Test
  void shouldReturnTransactionsFromMongoDB() throws Exception {
    TransactionDocument[] clientMock =
        mapJsonFileToObject(SERVICE_MOCK, TransactionDocument[].class);
    given(transactionRepository.findAll()).willReturn(Flux.fromArray(clientMock));
    List<TransactionDocument> response = service.getAllFromMongoDB();
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(5);
    assertThat(response.get(0).getCategory()).isEqualTo("Coffee Purchase");
  }
}
