package com.github.rusakovichma.persistance.authenticated.poc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Employee implements Serializable {

    private Long id;
    @NotNull
    @NotEmpty
    private String firstname;
    @NotNull
    @NotEmpty
    private String lastname;
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String position;
    @JsonIgnore
    private String salaryEncrypted;
    @JsonIgnore
    private String nonce;
    private transient Integer salary;

    public Employee() {
    }

    public Employee(Long id, @NotNull @NotEmpty String firstname, @NotNull @NotEmpty String lastname, @NotNull @NotEmpty String email, @NotNull @NotEmpty String position, Integer salary) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }

    public Employee(@NotNull @NotEmpty String firstname, @NotNull @NotEmpty String lastname, @NotNull @NotEmpty String email, @NotNull @NotEmpty String position, Integer salary) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalaryEncrypted() {
        return salaryEncrypted;
    }

    public void setSalaryEncrypted(String salaryEncrypted) {
        this.salaryEncrypted = salaryEncrypted;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                '}';
    }
}
