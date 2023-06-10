package com.tanylog.exception.customException;

/**
 * 정책 상 status - 404
 */

public class PostNotFound extends GlobalException {

  private static final String MESSAGE = "존재하지 않는 글입니다.";

  public PostNotFound() {
    super(MESSAGE);
  }

  public PostNotFound(Throwable cause) {
    super(MESSAGE, cause);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
