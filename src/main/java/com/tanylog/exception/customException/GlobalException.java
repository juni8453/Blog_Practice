package com.tanylog.exception.customException;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {

  private final Map<String, String> validation = new HashMap<>();

  public GlobalException(String message) {
    super(message);
  }

  public GlobalException(String message, Throwable cause) {
    super(message, cause);
  }

  public abstract int getStatusCode();

  public void addValidation(String fieldName, String message) {
    validation.put(fieldName, message);
  }
}
