package com.github.rusakovichma.persistance.authenticated.poc.service.exception;

import java.io.IOException;

public class EmployeeCreateException extends IOException {
    public EmployeeCreateException() {
    }

    public EmployeeCreateException(String message) {
        super(message);
    }

    public EmployeeCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeCreateException(Throwable cause) {
        super(cause);
    }
}
