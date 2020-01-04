package com.github.rusakovichma.persistance.authenticated.poc.service.exception;

import java.security.GeneralSecurityException;

public class EmployeeDecryptionException extends GeneralSecurityException {

    public EmployeeDecryptionException() {
    }

    public EmployeeDecryptionException(String msg) {
        super(msg);
    }

    public EmployeeDecryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeDecryptionException(Throwable cause) {
        super(cause);
    }
}
