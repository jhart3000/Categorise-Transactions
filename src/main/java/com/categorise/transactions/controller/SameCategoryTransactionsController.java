package com.categorise.transactions.controller;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.service.SameCategoryTransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SameCategoryTransactionsController {

  @Autowired private SameCategoryTransactionService service;

  @GetMapping("/getTransactionsWithSameCategory")
  @ApiOperation(
      value = "Get the transactions of a specific category",
      notes =
          "This api will return a list of all the transactions that are of the same category as the category string that is passed into the path param",
      response = TransactionDocument[].class)
  public List<TransactionDocument> getTransactionsWithSameCategory(
      @ApiParam(
              value =
                  "The category of a transaction. Only transactions of this category will be returned e.g. Coffee Purchase",
              required = true)
          @RequestHeader("Category")
          String category)
      throws ApplicationException {
    return service.getTransactionsWithSameCategory(category);
  }
}
