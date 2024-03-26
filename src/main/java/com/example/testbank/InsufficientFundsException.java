package com.example.testbank;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Недостаточно средств на счете");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
