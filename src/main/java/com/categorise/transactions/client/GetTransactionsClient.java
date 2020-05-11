package com.categorise.transactions.client;

import com.categorise.transactions.mongodb.TransactionDocument;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ResourceUtils.getFile;

public class GetTransactionsClient {

  public List<TransactionDocument> getTransactionsHardCoded() throws IOException {
    File file = getFile("classpath:transaction_response.json");
    InputStream input = new FileInputStream(file);
    ObjectMapper mapper = new ObjectMapper();
    return Arrays.asList(mapper.readValue(input, TransactionDocument[].class));
  }
}
