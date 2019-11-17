package com.categorise.transactions.fractal.model;

import lombok.Data;

@Data
public class CategoriseTransactionsRequest {
    private int bankId;
    private int accountId;
    private String authorizationToken;
    private String apiKey;
    private String partnerId;
}
