package com.github.rusakovichma.persistance.authenticated.poc.controller;

import com.github.rusakovichma.persistance.authenticated.poc.EmployeesApplication;
import com.github.rusakovichma.persistance.authenticated.poc.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = EmployeesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersistenceSalaryTamperingAttack {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Sql({"classpath:schema_h2_cleaning.sql", "classpath:schema_h2_salary_tampering.sql"})
    @Test
    public void testGetEmployee() {
        Employee ceoEmployee = restTemplate.getForObject(String.format("http://localhost:%d/employees/1", port), Employee.class);
        assertEquals(99999, (int) ceoEmployee.getSalary());
        assertNull(ceoEmployee.getSalaryEncrypted());
        assertNull(ceoEmployee.getNonce());

        ResponseEntity<Employee> devEmployee = restTemplate.getForEntity(String.format("http://localhost:%d/employees/999", port), Employee.class);
        System.out.println(devEmployee);
        //Tampering detected
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), devEmployee.getStatusCodeValue());
    }


}