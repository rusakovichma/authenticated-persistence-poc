package com.github.rusakovichma.persistance.authenticated.poc.repository;

import com.github.rusakovichma.persistance.authenticated.poc.model.Employee;

public interface EmployeesRepository {

    public Employee save(Employee user);

    public Employee update(Employee employee);

    public Employee getById(Long id);

}
