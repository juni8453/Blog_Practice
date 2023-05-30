package com.tanylog.exception.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {
 *   "code": "400",
 *   "message": "잘못된 요청입니다.",
 *   "validation": {
 *     "title": "title 을 입력해주세요.",
 *     "content": "content" 을 입력해주세요."
 *   }
 * }
 */

@Getter
public class ErrorResponse {

  private final String code;
  private final String message;
  private final Map<String, String> validation = new HashMap<>();

  @Builder
  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public void addValidation(String field, String errorMessage) {
    this.validation.put(field, errorMessage);
  }
}
