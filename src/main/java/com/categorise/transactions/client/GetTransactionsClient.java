package com.categorise.transactions.client;

import com.categorise.transactions.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ResourceUtils.getFile;

public class GetTransactionsClient {

  public List<Transaction> getTransactionsHardCoded() throws Exception {
    File file = getFile("classpath:transaction_response.json");
    InputStream input = new FileInputStream(file);
    ObjectMapper mapper = new ObjectMapper();
    return Arrays.asList(mapper.readValue(input, Transaction[].class));
  }
}
