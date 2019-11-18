package com.categorise.transactions.fractal.helper;

import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;

public class Constants {

  public static CategoriseTransactionsRequest CLIENT_REQUEST =
      CategoriseTransactionsRequest.builder()
          .accountId("fakeAcc62")
          .apiKey("5678")
          .authorizationToken("Bearer 1234")
          .bankId(2)
          .partnerId("9101112")
          .build();
}
