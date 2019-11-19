package com.categorise.transactions.fractal.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddCategoryController {

  @PutMapping("/categorySearch/{categorySearch}/categoryName/{categoryName}/addCategory")
  @ApiOperation(
      value = "Add a new category to all transactions",
      notes =
          "This api will check the description string of all transactions and if it contains the categorySearch string passed in the path param, it will update the category of that transaction to the categoryName passed in the path param")
  public void addCategory() {}
}
