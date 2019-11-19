package com.categorise.transactions.fractal.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryRequest {
  private String category;
  private String transactionId;
}
