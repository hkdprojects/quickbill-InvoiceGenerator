package com.invoice_generator.quickbill.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}

