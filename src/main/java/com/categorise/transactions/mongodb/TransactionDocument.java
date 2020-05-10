package com.categorise.transactions.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class TransactionDocument {
  @Id private String transactionId;
  private int bankId;
  private String accountId;
  private int companyId;
  private String bookingDate;
  private String description;
  private String amount;
  private String currencyCode;
  private String type;
  private String category;
}
