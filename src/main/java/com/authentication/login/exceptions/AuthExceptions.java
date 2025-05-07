package com.authentication.login.exceptions;

import com.authentication.login.util.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthExceptions extends Throwable {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
      super(message);
    }

    public HttpStatus getStatus() {
      return HttpStatus.NOT_FOUND;
    }
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public static class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
      super(message);
    }

    public HttpStatus getStatus() {
      return HttpStatus.UNAUTHORIZED;
    }
  }

  @ControllerAdvice
  public static class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<GenericResponse<String>> handleException(RuntimeException ex) {
      HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
      if (ex instanceof NotFoundException) {
        status = HttpStatus.NOT_FOUND;
      } else if (ex instanceof IncorrectPasswordException) {
        status = HttpStatus.UNAUTHORIZED;
      }

      GenericResponse<String> response = new GenericResponse<>(status, ex.getMessage());
      return new ResponseEntity<>(response, status);
    }
  }
}
