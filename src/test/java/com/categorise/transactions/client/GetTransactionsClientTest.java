package com.categorise.transactions.client;

import com.categorise.transactions.mongodb.TransactionDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GetTransactionsClientTest {

  @Autowired private GetTransactionsClient client;

  @Test
  void shouldReturnSuccessfulClientResponse() throws Exception {
    List<TransactionDocument> response = client.getTransactionsHardCoded();

    assertThat(response).isNotNull();
    assertThat(response.get(0).getDescription()).isEqualTo("Starbucks Victoria Stn");
    assertThat(response.size()).isEqualTo(33);
  }
}
