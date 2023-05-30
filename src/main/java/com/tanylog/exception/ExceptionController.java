package com.tanylog.exception;

import com.tanylog.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
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
    ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");

    for (FieldError fieldError : exception.getFieldErrors()) {
      response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return response;
  }
}
