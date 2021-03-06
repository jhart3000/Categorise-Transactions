package com.categorise.transactions.controller;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriseTransactionsController {

  @Autowired private CategoriseTransactionsService service;

  @GetMapping("/categoriseTransactions/cache/{useCache}")
  @ApiOperation(
      value = "Categorise transactions based on coffee and amazon purchases",
      notes =
          "This api will get a hardcoded list of transactions and save them to MongoDB. If the useCache is set to true it will retrieve existing data from MongoDB. "
              + "It will then categorise the transactions based on the transaction description into two categories: coffee purchases and amazon purchases. "
              + "The rest will be labelled as not categorised. Every time this api is called the new list is updated in MongoDB.",
      response = TransactionDocument[].class)
  public List<TransactionDocument> categoriseTransactions(
      @ApiParam(
              value =
                  "Set to true if you want to load existing data from MongoDB and false if you want to call an external client and save that data in MongoDB",
              required = true)
          @PathVariable("useCache")
          boolean useCache)
      throws ApplicationException {
    return service.categoriseTransactions(useCache);
  }
}
