package com.categorise.transactions.controller;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.UpdateCategoryRequest;
import com.categorise.transactions.service.CategoriseTransactionsService;
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
          "This api will select the transaction based on the transaction id passed in the request body and replace its category field with the category string passed in the body",
      response = String.class)
  public String updateTransaction(
      @ApiParam(
              value =
                  "The request body for this api which contains two fields. The new category that you would like to add to the existing transaction and transaction id of the transaction you would like to update",
              required = true)
          @RequestBody
          UpdateCategoryRequest requestBody)
      throws ApplicationException {

    String transactionId = requestBody.getTransactionId();
    String category = requestBody.getCategory();

    if (transactionId == null || category == null) {
      throw new ApplicationException(
          "Invalid Request Body: Please pass transactionId and category in request body");
    }
    service.updateTransaction(transactionId, category);
    return "Transaction Category Updated";
  }
}
