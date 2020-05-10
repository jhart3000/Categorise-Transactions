package com.categorise.transactions.controller;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.service.MongoDBInteractionsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrentListController {

  @Autowired private MongoDBInteractionsService service;

  @GetMapping("/getAllTransactions")
  @ApiOperation(
      value = "Returns the current list of categorised transactions",
      notes =
          "This api returns the most up to date list of categorised transactions so the user can see the changes that have been made",
      response = TransactionDocument[].class)
  public List<TransactionDocument> getTransactions() throws ApplicationException {
    return service.getAllFromMongoDB();
  }
}
