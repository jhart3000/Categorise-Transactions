package com.categorise.transactions.controller;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.Transaction;
import com.categorise.transactions.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrentListController {

  @Autowired private CategoriseTransactionsService service;

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
