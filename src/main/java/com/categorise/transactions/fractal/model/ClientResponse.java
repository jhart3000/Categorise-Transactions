package com.categorise.transactions.fractal.model;

import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
  private List<Transaction> results;
}
