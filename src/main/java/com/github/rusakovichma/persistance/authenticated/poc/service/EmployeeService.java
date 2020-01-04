package com.github.rusakovichma.persistance.authenticated.poc.service;

import com.github.rusakovichma.persistance.authenticated.poc.model.Employee;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeCreateException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeDecryptionException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeEncryptionException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeNotFoundException;

public interface EmployeeService {

    public void createEmployee(Employee employee) throws EmployeeCreateException, EmployeeEncryptionException;

    public Employee findEmployee(Long id) throws EmployeeNotFoundException, EmployeeDecryptionException;

}
