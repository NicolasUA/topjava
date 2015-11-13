package ru.javawebinar.topjava.util.exception;

/**
 * Created by Nicolas on 13.11.2015.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
