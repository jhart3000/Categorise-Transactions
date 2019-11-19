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
  public static final String CLIENT_URL =
      "https://sandbox.askfractal.com/banking/2/accounts/fakeAcc62/transactions";
  public static final String UPDATE_CATEGORY_REQUEST =
      "{    \n"
          + "\t\"transactionId\": \"0ef942ea-d3ad-4f25-857b-4d4bb7f912d8\",    \n"
          + "    \"category\": \"Updated Category\"\n"
          + "} ";
  public static final String ADD_CATEGORY =
      "{    \n"
          + "\t\"descriptionSearch\": [\"BT\", \"Mobile\"],    \n"
          + "    \"newCategory\": \"Added Category\"\n"
          + "} ";
  public static final String INVALID_BODY =
      "{    \n"
          + "\t\"invalid\": \"0ef942ea-d3ad-4f25-857b-4d4bb7f912d8\",    \n"
          + "    \"invalid2\": \"Updated Category\"\n"
          + "} ";
}
