package com.tanylog.exception.customException;

/**
 * 정책 상 status - 400
 */

public class InValidRequest extends GlobalException {

  private static final String MESSAGE = "잘못된 요청입니다.";

  public InValidRequest() {
    super(MESSAGE);
  }

  // InValidRequest 생성 시점에 fieldName, message 삽입
  public InValidRequest(String fieldName, String message) {
    super(MESSAGE);
    addValidation(fieldName, message);
  }

  @Override
  public int getStatusCode() {
    return 400;
  }
}
