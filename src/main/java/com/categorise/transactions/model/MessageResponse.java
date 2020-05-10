package com.categorise.transactions.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
  private String message;
}
