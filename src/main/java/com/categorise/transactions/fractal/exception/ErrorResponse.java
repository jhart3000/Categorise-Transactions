package com.categorise.transactions.fractal.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
  private String message;
}
