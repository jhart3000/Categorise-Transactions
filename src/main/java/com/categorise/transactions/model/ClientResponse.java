package com.categorise.transactions.model;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
  private List<Transaction> results;
}
