package com.categorise.transactions.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriseTransactionsRequest {
  private int bankId;
  private String accountId;
  private String authorizationToken;
  private String apiKey;
  private String partnerId;
}
