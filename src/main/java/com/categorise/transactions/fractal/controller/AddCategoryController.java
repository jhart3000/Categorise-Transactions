package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.exception.ApplicationException;
import com.categorise.transactions.fractal.model.AddCategoryRequest;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddCategoryController {

  @Autowired private CategoriseTransactionsService service;

  @PutMapping("/addCategory")
  @ApiOperation(
      value = "Add a new category to all transactions",
      notes =
          "This api will check the description string of all transactions and if it contains the categorySearch string passed in the path param, it will update the category of that transaction to the categoryName passed in the path param",
      response = String.class)
  public String addCategory(
      @ApiParam(
              value =
                  "The request body for this api which contains two fields. A list with the strings you would like to search the description with and the new category name you would like to assign to transactions that contain these strings in their description",
              required = true)
          @RequestBody
          AddCategoryRequest requestBody)
      throws ApplicationException {

    if (requestBody.getDescriptionSearch() == null || requestBody.getNewCategory() == null) {
      throw new ApplicationException(
          "Invalid Request Body: Please pass getDescriptionSearch and getNewCategory in request body");
    }
    service.addCategory(requestBody);
    return "Category Successfully Added";
  }
}
