package com.categorise.transactions.controller;

import com.categorise.transactions.model.Transaction;
import com.categorise.transactions.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriseTransactionsController {

  @Autowired private CategoriseTransactionsService service;

  @GetMapping("/categoriseTransactions")
  @ApiOperation(
      value = "Categorise transactions based on coffee and amazon purchases",
      notes =
          "This api will call the get transactions api using the bank id and account id in the path param and the same headers passed into this api. "
              + "It will then categorise the transactions based on the transaction description into two categories: coffee purchases and amazon purchases. "
              + "The rest will be labelled as not categorised. Every time this api is called all changes to category will be reset to values returned when this api was first called",
      response = Transaction[].class)
  public List<Transaction> categoriseTransactions() throws Exception {
    return service.categoriseTransactions();
  }
}
