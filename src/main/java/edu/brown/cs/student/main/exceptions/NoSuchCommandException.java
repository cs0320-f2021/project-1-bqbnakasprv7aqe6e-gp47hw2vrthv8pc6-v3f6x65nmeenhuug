package edu.brown.cs.student.main.exceptions;

public class NoSuchCommandException extends Exception {
  public NoSuchCommandException(String errorMessage) {
    super(errorMessage);
  }
}
