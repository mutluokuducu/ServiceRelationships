package com.interlinksoftware.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public enum ErrorType {
  INTERNAL_ERROR(100, "An internal server error occurred", INTERNAL_SERVER_ERROR),
  INVALID_PARAMETER_ERROR(1001, "Invalid field(s). ", BAD_REQUEST),
  INVALID_PARENT_OR_CHILD(1002, "Invalid parent or child id. ", NOT_FOUND);

  private int id;
  private String description;
  private HttpStatus httpStatus;

  ErrorType(int id, String description, HttpStatus httpStatus) {

    this.id = id;
    this.description = description;
    this.httpStatus = httpStatus;
  }
}
