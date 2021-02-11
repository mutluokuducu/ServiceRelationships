package com.interlinksoftware.exception;

import static com.interlinksoftware.exception.ErrorType.INVALID_PARAMETER_ERROR;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ServiceRelationshipsException.class)
  @Order(HIGHEST_PRECEDENCE)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleAdapterException(
      ServiceRelationshipsException exception) {
    ErrorType errorMessage = exception.getErrorType();
    HttpStatus httpStatus = errorMessage.getHttpStatus();
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .error(Error.builder()
                .id(errorMessage.getId())
                .description(errorMessage.getDescription())
                .build())
            .build(),
        httpStatus);
  }

  @ExceptionHandler(Exception.class)
  @Order(HIGHEST_PRECEDENCE)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleException(
      Exception exception) {
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .error(Error.builder()
                .description(exception.getMessage())
                .build())
            .build(),
        INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<Error> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {

    ErrorType errorType = INVALID_PARAMETER_ERROR;
    List<String> fields = new ArrayList<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(e -> fields.add(e.getField() + ": " + e.getDefaultMessage()));
    Error errorMessage = createError(errorType, exception);
    return new ResponseEntity<>(errorMessage, errorType.getHttpStatus());
  }

  private Error createError(ErrorType errorType, Exception exception) {
    log.error("Error occurred. {}", exception.getMessage());

    return Error.builder()
        .id(errorType.getId())
        .description(errorType.getDescription())
        .build();
  }
}