package com.categorise.transactions.fractal.helper;

import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;

public class Constants {

  public static final CategoriseTransactionsRequest CLIENT_REQUEST =
      CategoriseTransactionsRequest.builder()
          .accountId("fakeAcc62")
          .apiKey("5678")
          .authorizationToken("Bearer 1234")
          .bankId(2)
          .partnerId("9101112")
          .build();

  public static final String SERVICE_MOCK = "get-transactions-service-mock.json";
  public static final String COFFEE_PURCHASE = "Coffee Purchase";
  public static final String UPDATED_CATEGORY = "Updated Category";
  public static final String AMAZON_PURCHASE = "Amazon Purchase";
}
