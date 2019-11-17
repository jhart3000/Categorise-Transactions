package com.categorise.transactions.fractal.client;

import com.categorise.transactions.fractal.beans.TestBeans;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.ClientResponse;
import com.categorise.transactions.fractal.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestBeans.class)
public class GetTransactionsClientTest {

  private final String CLIENT_RESPONSE_PATH = "get-transactions-client-response.json";
  private final String CLIENT_URL =
      "https://sandbox.askfractal.com/banking/2/accounts/fakeAcc62/transactions";
  private HttpEntity<?> entity;

  @MockBean RestTemplate restTemplate;

  @Autowired GetTransactionsClient client;

  @BeforeEach
  void setup() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer 1234");
    headers.set("X-Api-Key", "5678");
    headers.set("X-Partner-Id", "9101112");
    entity = new HttpEntity<>(headers);
  }

  @Test
  void shouldReturnSuccessfulClientResponse() throws Exception {
    ClientResponse clientResponse = mapJsonFileToObject(CLIENT_RESPONSE_PATH, ClientResponse.class);
    Mockito.when(restTemplate.exchange(CLIENT_URL, HttpMethod.GET, entity, ClientResponse.class))
        .thenReturn(new ResponseEntity<>(clientResponse, HttpStatus.OK));

    List<Transaction> response =
        client.getTransactions(
            buildClientRequest(2, "fakeAcc62", "Bearer 1234", "5678", "9101112"));

    assertThat(response).isNotNull();
    assertThat(response.get(0).getDescription()).isEqualTo("Starbucks Victoria Stn");
    assertThat(response.size()).isEqualTo(33);
  }

  @Test
  void shouldReturnAuthErrorFromClient() {
    Mockito.when(restTemplate.exchange(CLIENT_URL, HttpMethod.GET, entity, ClientResponse.class))
        .thenThrow(new RuntimeException("401 Unauthorized"));

    Throwable errorResponse =
        catchThrowable(
            () ->
                client.getTransactions(
                    buildClientRequest(2, "fakeAcc62", "Bearer 1234", "5678", "9101112")));

    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.getMessage()).isEqualTo("401 Unauthorized");
  }

  private CategoriseTransactionsRequest buildClientRequest(
      int bankId, String accountId, String authToken, String apiKey, String partnerId) {
    return CategoriseTransactionsRequest.builder()
        .accountId(accountId)
        .apiKey(apiKey)
        .authorizationToken(authToken)
        .bankId(bankId)
        .partnerId(partnerId)
        .build();
  }
}
