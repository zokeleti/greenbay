package com.greenfox.greenbay.models.exceptions;

public class MissingFieldException extends Exception {
  public MissingFieldException(String message) {
    super("The following fields are missing: " + message);
  }
}
