package com.github.rusakovichma.persistance.authenticated.poc.service.exception;

import java.security.GeneralSecurityException;

public class EmployeeEncryptionException extends GeneralSecurityException {

    public EmployeeEncryptionException() {
    }

    public EmployeeEncryptionException(String msg) {
        super(msg);
    }

    public EmployeeEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeEncryptionException(Throwable cause) {
        super(cause);
    }
}
