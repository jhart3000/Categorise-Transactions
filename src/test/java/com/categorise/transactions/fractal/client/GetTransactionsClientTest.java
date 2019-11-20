package com.categorise.transactions.fractal.client;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.ClientResponse;
import com.categorise.transactions.fractal.model.Transaction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.categorise.transactions.fractal.helper.Constants.*;
import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = BeanDefinitions.class)
class GetTransactionsClientTest {

  @MockBean private RestTemplate restTemplate;

  @Autowired private GetTransactionsClient client;

  @Test
  void shouldReturnSuccessfulClientResponse() throws Exception {
    ClientResponse clientResponse =
        mapJsonFileToObject(
            "responses/get-transactions-client-response.json", ClientResponse.class);
    Mockito.when(
            restTemplate.exchange(CLIENT_URL, HttpMethod.GET, HTTP_ENTITY, ClientResponse.class))
        .thenReturn(new ResponseEntity<>(clientResponse, HttpStatus.OK));

    List<Transaction> response = client.getTransactions(CLIENT_REQUEST);

    assertThat(response).isNotNull();
    assertThat(response.get(0).getDescription()).isEqualTo("Starbucks Victoria Stn");
    assertThat(response.size()).isEqualTo(33);
  }

  @Test
  void shouldReturnAuthErrorFromClient() {
    Mockito.when(
            restTemplate.exchange(CLIENT_URL, HttpMethod.GET, HTTP_ENTITY, ClientResponse.class))
        .thenThrow(new RuntimeException("401 Unauthorized"));

    Throwable errorResponse = catchThrowable(() -> client.getTransactions(CLIENT_REQUEST));

    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.getMessage()).isEqualTo("401 Unauthorized");
  }

  @Test
  void shouldReturnNullFromClient() {
    Throwable errorResponse = catchThrowable(() -> client.getTransactions(CLIENT_REQUEST));

    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.getMessage())
        .isEqualTo("Error Encountered When Trying To Retrieve Bank Transactions");
  }
}
