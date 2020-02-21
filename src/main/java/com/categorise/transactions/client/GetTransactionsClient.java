package com.categorise.transactions.client;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.ClientResponse;
import com.categorise.transactions.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.ResourceUtils.getFile;

public class GetTransactionsClient {
  public List<Transaction> getTransactionsHardCoded() throws ApplicationException {
    try {
      ClientResponse clientResponse =
          mapJsonFileToObject("get-transactions-client-response.json", ClientResponse.class);
      return Objects.requireNonNull(clientResponse).getResults();
    } catch (Exception e) {
      throw new ApplicationException(e.getMessage());
    }
  }

  private static <T> T mapJsonFileToObject(String path, Class<T> responseType) throws Exception {
    File file = getFile("classpath:" + path);
    InputStream input = new FileInputStream(file);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(input, responseType);
  }
}
