package com.agritrack.exception;

/**
 * Used when a requested farmer cannot be found.
 */
public class FarmerNotFoundException extends RuntimeException {

    public FarmerNotFoundException(String message) {
        super(message);
    }
}
