package com.categorise.transactions.controller;

import com.categorise.transactions.exception.ApplicationException;
import com.categorise.transactions.model.AddCategoryRequest;
import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.service.AddCategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddCategoryController {

  @Autowired private AddCategoryService service;

  @PutMapping("/addCategory")
  @ApiOperation(
      value = "Add a new category to all transactions",
      notes =
          "This api will check the description string of all transactions and if it contains any of the strings in the descriptionSearch list passed in the body, it will update the category of that transaction to the newCategory field in the request body",
      response = String.class)
  public MessageResponse addCategory(
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
    return service.addCategory(requestBody);
  }
}
