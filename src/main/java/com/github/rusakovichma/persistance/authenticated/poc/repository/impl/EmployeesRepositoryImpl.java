package com.github.rusakovichma.persistance.authenticated.poc.repository.impl;

import com.github.rusakovichma.persistance.authenticated.poc.model.Employee;
import com.github.rusakovichma.persistance.authenticated.poc.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

@Repository
public class EmployeesRepositoryImpl implements EmployeesRepository {

    private Logger logger = Logger.getLogger(EmployeesRepositoryImpl.class.getName());

    private class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("ID"));
            employee.setFirstname(rs.getString("FIRST_NAME"));
            employee.setLastname(rs.getString("LAST_NAME"));
            employee.setEmail(rs.getString("EMAIL"));
            employee.setPosition(rs.getString("POSITION_TITLE"));
            employee.setSalaryEncrypted(rs.getString("SALARY_ENCRYPTED"));
            employee.setNonce(rs.getString("SALARY_ENCRYPTED_NONCE"));
            return employee;
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Employee save(Employee employee) {
        long generatedEmployeeId = jdbcTemplate.update("INSERT INTO EMPLOYEES (ID, FIRST_NAME, LAST_NAME, EMAIL, POSITION_TITLE, SALARY_ENCRYPTED, SALARY_ENCRYPTED_NONCE) VALUES(?, ?, ?, ?, ?, ?, ?)",
                new Object[]{employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getEmail(), employee.getPosition(), employee.getSalaryEncrypted(), employee.getNonce()});
        if (employee.getId() == null) {
            employee.setId(generatedEmployeeId);
        }
        return employee;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Employee update(Employee employee) {
        jdbcTemplate.update("UPDATE EMPLOYEES SET (FIRST_NAME, LAST_NAME, EMAIL, POSITION_TITLE, SALARY_ENCRYPTED, SALARY_ENCRYPTED_NONCE) = (?, ?, ?, ?, ?, ?) WHERE ID = ?",
                new Object[]{employee.getFirstname(), employee.getLastname(), employee.getEmail(), employee.getPosition(), employee.getSalaryEncrypted(), employee.getNonce(), employee.getId()});
        return employee;
    }

    @Override
    public Employee getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT emp.ID, emp.FIRST_NAME, emp.LAST_NAME, emp.EMAIL, emp.POSITION_TITLE, emp.SALARY_ENCRYPTED, emp.SALARY_ENCRYPTED_NONCE  " +
                            "FROM EMPLOYEES emp " +
                            "WHERE emp.ID=?",
                    new Object[]{id},
                    new EmployeeRowMapper()
            );
        } catch (Exception ex) {
            logger.severe(String.format("Couldn't find the employee [%d] :", id) + ex.getMessage());
            return null;
        }
    }
}
