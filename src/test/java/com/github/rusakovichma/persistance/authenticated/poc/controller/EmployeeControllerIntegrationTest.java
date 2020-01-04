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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = EmployeesApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql({"classpath:schema_h2_cleaning.sql"})
    @Test
    @Rollback(false)
    public void testCreateCeoEmployee() {
        Employee ceoEmployee = new Employee(1L, "John", "Smith", "john_smith@companyname.com", "CEO", 99999);

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(String.format("http://localhost:%d/employees/", port), ceoEmployee, String.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Sql({"classpath:schema_h2_cleaning.sql"})
    @Test
    @Rollback(false)
    public void testCreateDevEmployee() {
        Employee devEmployee = new Employee(999L, "Joe", "Doe", "joe_doe@companyname.com", "Software Engineer", 1000);

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(String.format("http://localhost:%d/employees/", port), devEmployee, String.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }


    @Sql({"classpath:schema_h2_cleaning.sql", "classpath:schema_h2_data.sql"})
    @Test
    public void testGetEmployee() {
        Employee ceoEmployee = restTemplate.getForObject(String.format("http://localhost:%d/employees/1", port), Employee.class);
        assertEquals(99999, (int) ceoEmployee.getSalary());
        assertNull(ceoEmployee.getSalaryEncrypted());
        assertNull(ceoEmployee.getNonce());

        Employee devEmployee = restTemplate.getForObject(String.format("http://localhost:%d/employees/999", port), Employee.class);
        assertEquals(1000, (int) devEmployee.getSalary());
        assertNull(devEmployee.getSalaryEncrypted());
        assertNull(devEmployee.getNonce());
    }


}