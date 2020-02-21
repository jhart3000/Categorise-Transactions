package com.categorise.transactions.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
  private String message;
}
