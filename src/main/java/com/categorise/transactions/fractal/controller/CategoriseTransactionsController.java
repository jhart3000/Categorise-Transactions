package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.exception.ClientException;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.Transaction;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriseTransactionsController {

  @Autowired private CategoriseTransactionsService service;

  @GetMapping("/banking/{bankId}/accounts/{accountId}/categoriseTransactions")
  @ApiOperation(
      value = "Categorise transactions based on coffee and amazon purchases",
      notes =
          "This api will call the get transactions api using the bank id and account id in the path param and the same headers passed into this api. "
              + "It will then categorise the transactions based on the transaction description into two categories: coffee purchases and amazon purchases. "
              + "The rest will be labelled as not categorised. Every time this api is called all changes to category will be reset to values returned when this api was first called",
      response = Transaction[].class)
  public List<Transaction> categoriseTransactions(
      @ApiParam(
              value = "Unique identifier for the bank selected by the user. e.g. 2",
              required = true)
          @PathVariable("bankId")
          int bankId,
      @ApiParam(
              value = "Unique identifier for the account selected by the user. e.g. fakeAcc62",
              required = true)
          @PathVariable("accountId")
          String accountId,
      @ApiParam(
              value = "Authorization generated using users credentials. e.g. Bearer eyJhbGxci...",
              required = true)
          @RequestHeader("Authorization")
          String authorizationToken,
      @ApiParam(
              value =
                  "The hidden key provided in the Fractal Developer Portal after creating an app",
              required = true)
          @RequestHeader("X-Api-Key")
          String apiKey,
      @ApiParam(
              value = "The partner id provided in the FractalSandbox postman environment setup",
              required = true)
          @RequestHeader("X-Partner-Id")
          String partnerId)
      throws ClientException {

    CategoriseTransactionsRequest request =
        CategoriseTransactionsRequest.builder()
            .accountId(accountId)
            .apiKey(apiKey)
            .authorizationToken(authorizationToken)
            .bankId(bankId)
            .partnerId(partnerId)
            .build();

    return service.categoriseTransactions(request);
  }

  @GetMapping("/category/{category}/getTransactionsWithSameCategory")
  @ApiOperation(
      value = "Get the transactions of a specific category",
      notes =
          "This api will return a list of all the transactions that are of the same category as the category string that is passed into the path param",
      response = Transaction[].class)
  public List<Transaction> getTransactionsWithSameCategory(
      @ApiParam(
              value =
                  "The category of a transaction. Only transactions of this category will be returned e.g. Coffee Purchase",
              required = true)
          @PathVariable("category")
          int category) {
    return null;
  }

  @PutMapping("/transaction/{transactionId}/category/{category}/updateTransactionCategory")
  @ApiOperation(
      value = "Update the category of a specific transaction",
      notes =
          "This api will select the transaction based on the id passed int he path param and replace its category field with the category string passed in the path param")
  public void updateTransaction(
      @ApiParam(value = "The unique identifier for each transaction", required = true)
          @PathVariable("transactionId")
          int transactionId,
      @ApiParam(
              value = "The new category that you would like to add to the existing transaction",
              required = true)
          @PathVariable("category")
          int category) {}

  @PutMapping("/categorySearch/{categorySearch}/categoryName/{categoryName}/addCategory")
  @ApiOperation(
      value = "Add a new category to all transactions",
      notes =
          "This api will check the description string of all transactions and if it contains the categorySearch string passed in the path param, it will update the category of that transaction to the categoryName passed in the path param")
  public void addCategory() {}

  @GetMapping("getAllTransactions")
  @ApiOperation(
      value = "Returns the current list of categorised transactions",
      notes =
          "This api returns the most up to date list of categorised transactions so the user can see the changes that have been made",
      response = Transaction[].class)
  public List<Transaction> getTransactions() {
    return null;
  }
}
