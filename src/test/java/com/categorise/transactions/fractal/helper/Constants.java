package com.categorise.transactions.fractal.helper;

import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class Constants {

  public static final CategoriseTransactionsRequest CLIENT_REQUEST =
      CategoriseTransactionsRequest.builder()
          .accountId("fakeAcc62")
          .apiKey("5678")
          .authorizationToken("Bearer 1234")
          .bankId(2)
          .partnerId("9101112")
          .build();

  public static final String SERVICE_MOCK = "responses/get-transactions-service-mock.json";
  public static final String COFFEE_PURCHASE = "Coffee Purchase";
  public static final String UPDATED_CATEGORY = "Updated Category";
  public static final String AMAZON_PURCHASE = "Amazon Purchase";
  public static final String CLIENT_URL =
      "https://sandbox.askfractal.com/banking/2/accounts/fakeAcc62/transactions";
  public static final String UPDATE_CATEGORY_REQUEST = "requests/update-category-request.json";
  public static final String ADD_CATEGORY_REQUEST = "requests/add-category-request.json";
  public static final String INVALID_BODY = "requests/invalid-request-body.json";
  public static final HttpEntity<?> HTTP_ENTITY = createEntityConstant();

  private static HttpEntity<?> createEntityConstant() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer 1234");
    headers.set("X-Api-Key", "5678");
    headers.set("X-Partner-Id", "9101112");
    return new HttpEntity<>(headers);
  }
}
