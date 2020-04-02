package com.categorise.transactions.client;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ResourceUtils.getFile;

public class GetTransactionsClient {

  public List<Transaction> getTransactionsHardCoded() throws ApplicationException {
    try {
      return Arrays.asList(getTransactionFromJsonFile());
    } catch (Exception e) {
      throw new ApplicationException(e.getMessage());
    }
  }

  private Transaction[] getTransactionFromJsonFile() throws Exception {
    File file = getFile("classpath:transaction_response.json");
    InputStream input = new FileInputStream(file);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(input, Transaction[].class);
  }
}

