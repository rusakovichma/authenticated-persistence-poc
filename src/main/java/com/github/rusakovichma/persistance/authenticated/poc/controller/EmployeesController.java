package com.github.rusakovichma.persistance.authenticated.poc.controller;

import com.github.rusakovichma.persistance.authenticated.poc.model.Employee;
import com.github.rusakovichma.persistance.authenticated.poc.service.EmployeeService;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeCreateException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeDecryptionException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeEncryptionException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/employees")
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.createEmployee(employee);
        } catch (EmployeeCreateException | EmployeeEncryptionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        try {
            Employee employee = employeeService.findEmployee(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (EmployeeNotFoundException enfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EmployeeDecryptionException ede) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
