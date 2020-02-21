package com.categorise.transactions.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCategoryRequest {

  @ApiModelProperty(example = "\"[\"BT\",\"MOBILE\"]\"")
  private String[] descriptionSearch;

  @ApiModelProperty(example = "Internet Purchases")
  private String newCategory;
}
