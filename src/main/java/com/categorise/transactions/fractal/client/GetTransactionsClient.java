package com.categorise.transactions.fractal.client;

import com.categorise.transactions.fractal.exception.ClientException;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.ClientResponse;
import com.categorise.transactions.fractal.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTransactionsClient {

  @Autowired private RestTemplate restTemplate;

  public List<Transaction> getTransactions(CategoriseTransactionsRequest request)
      throws ClientException {

    String url =
        "https://sandbox.askfractal.com/banking/{bankId}/accounts/{accountId}/transactions";
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
    Map<String, Object> pathParams = new HashMap<>();
    pathParams.put("bankId", request.getBankId());
    pathParams.put("accountId", request.getAccountId());
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", request.getAuthorizationToken());
    headers.set("X-Api-Key", request.getApiKey());
    headers.set("X-Partner-Id", request.getPartnerId());
    HttpEntity<?> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<ClientResponse> response =
          restTemplate.exchange(
              builder.buildAndExpand(pathParams).toUriString(),
              HttpMethod.GET,
              entity,
              ClientResponse.class);
      return response.getBody().getResults();
    } catch (Exception e) {
      throw new ClientException(e.getMessage());
    }
  }
}
