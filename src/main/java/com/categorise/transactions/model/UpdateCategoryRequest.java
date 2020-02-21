package com.categorise.transactions.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryRequest {

  @ApiModelProperty(example = "Bills")
  private String category;

  @ApiModelProperty(example = "f73921e2-8dd3-48fc-8096-cf7b8f10d5b7")
  private String transactionId;
}
