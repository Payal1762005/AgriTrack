package com.agritrack.exception;

/**
 * Used when a requested product cannot be found.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
