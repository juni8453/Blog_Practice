package com.tanylog.exception;

import com.tanylog.exception.customException.GlobalException;
import com.tanylog.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException exception) {
    ErrorResponse response = ErrorResponse.builder()
        .code("400")
        .message("잘못된 요청입니다.")
        .build();

    for (FieldError fieldError : exception.getFieldErrors()) {
      response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return response;
  }

  // Exception 종류에 따라 400 인지, 404 인지 결정
  // @ResponseStatus 를 사용하면 동적으로 StatusCode 를 변경할 수 없으므로 ResponseEntity<> 사용
//  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ErrorResponse> globalException(GlobalException exception) {
    int statusCode = exception.getStatusCode();

    ErrorResponse errorResponse = ErrorResponse.builder()
        .code(String.valueOf(statusCode))
        .message(exception.getMessage())
        .validation(exception.getValidation())
        .build();

    return ResponseEntity.status(statusCode)
        .body(errorResponse);
  }
}
