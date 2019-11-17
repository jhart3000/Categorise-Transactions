package com.categorise.transactions.fractal.model;

import lombok.Data;

@Data
public class Transaction {
    private int bankId;
    private String accountId;
    private int companyId;
    private String transactionId;
    private String bookingDate;
    private String description;
    private String amount;
    private String currencyCode;
    private String type;
    private String category;
}
