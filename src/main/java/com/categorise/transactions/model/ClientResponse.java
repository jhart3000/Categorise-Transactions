package com.categorise.transactions.model;

import com.categorise.transactions.mongodb.TransactionDocument;
import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
  private List<TransactionDocument> results;
}
