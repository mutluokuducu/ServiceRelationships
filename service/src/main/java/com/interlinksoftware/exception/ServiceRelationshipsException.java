package com.interlinksoftware.exception;

import lombok.Getter;

@Getter
public class ServiceRelationshipsException extends RuntimeException {

  private final ErrorType errorType;

  public ServiceRelationshipsException(ErrorType errorType) {
    this.errorType = errorType;
  }
}
