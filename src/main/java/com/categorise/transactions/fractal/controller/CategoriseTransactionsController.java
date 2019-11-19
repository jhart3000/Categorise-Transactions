package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.exception.ApplicationException;
import com.categorise.transactions.fractal.model.CategoriseTransactionsRequest;
import com.categorise.transactions.fractal.model.Transaction;
import com.categorise.transactions.fractal.model.UpdateCategoryRequest;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
      throws ApplicationException {

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

  @GetMapping("/getTransactionsWithSameCategory")
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
          @RequestHeader("Category")
          String category)
      throws ApplicationException {
    return service.getTransactionsWithSameCategory(category);
  }

  @PutMapping("/updateTransactionCategory")
  @ApiOperation(
      value = "Update the category of a specific transaction",
      notes =
          "This api will select the transaction based on the id passed in the request body and replace its category field with the category string passed in the body")
  public String updateTransaction(
      @ApiParam(
              value =
                  "The request body for this api which contains two fields. The new category that you would like to add to the existing transaction and transaction id of the transaction you would like to update",
              required = true)
          @RequestBody
          UpdateCategoryRequest requestBody)
      throws ApplicationException {
    service.updateTransaction(requestBody.getTransactionId(), requestBody.getCategory());
    return "Transaction Category Updated";
  }

  @PutMapping("/categorySearch/{categorySearch}/categoryName/{categoryName}/addCategory")
  @ApiOperation(
      value = "Add a new category to all transactions",
      notes =
          "This api will check the description string of all transactions and if it contains the categorySearch string passed in the path param, it will update the category of that transaction to the categoryName passed in the path param")
  public void addCategory() {}

  @GetMapping("/getAllTransactions")
  @ApiOperation(
      value = "Returns the current list of categorised transactions",
      notes =
          "This api returns the most up to date list of categorised transactions so the user can see the changes that have been made",
      response = Transaction[].class)
  public List<Transaction> getTransactions() throws ApplicationException {
    return service.returnCurrentTransactionList();
  }
}
