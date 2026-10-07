package com.agritrack.exception;

/**
 * Used when an operation requires more stock than is available.
 */
public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}
