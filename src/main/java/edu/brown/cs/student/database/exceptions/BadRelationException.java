package edu.brown.cs.student.database.exceptions;

public class BadRelationException extends Exception {
    public BadRelationException(String errorMessage) {
        super(errorMessage);
    }
}
