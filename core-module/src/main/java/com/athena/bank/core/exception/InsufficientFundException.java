package com.athena.bank.core.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class InsufficientFundException extends RuntimeException {
    public InsufficientFundException(String message) {
        super(message);
    }
    public InsufficientFundException(String message, Throwable cause) {
        super(message, cause);
    }
}
