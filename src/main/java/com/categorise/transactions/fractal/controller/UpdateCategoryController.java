package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.exception.ApplicationException;
import com.categorise.transactions.fractal.model.UpdateCategoryRequest;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateCategoryController {

  @Autowired private CategoriseTransactionsService service;

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
}
