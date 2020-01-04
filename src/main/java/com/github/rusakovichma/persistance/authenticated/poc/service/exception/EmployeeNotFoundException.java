package com.github.rusakovichma.persistance.authenticated.poc.service.exception;

import java.io.IOException;

public class EmployeeNotFoundException extends IOException {

    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmployeeNotFoundException(Throwable cause) {
        super(cause);
    }
}
