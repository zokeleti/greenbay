package com.greenfox.greenbay.models.exceptions;

public class MissingFieldException extends Exception {
  public MissingFieldException(String message) {
    super("The following field(s) are missing: " + message);
  }
}
