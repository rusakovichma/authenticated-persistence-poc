# Spring Boot Authenticated encryption persistence PoC
The PoC how to defend against tampering of encrypted data at persistence layer

Attack example
===============
An encryption is used to prevent information disclosure at DB layer, but it is not prevents sensitive data tampering.
Suppose, the encrypted employee salary field value were tampered with another employee higher salary stored in the same table.
```SQL
ID | First Name | Last Name | Email | Position | Salary (Encrypted)
1, 'John', 'Smith', 'john_smith@companyname.com', 'CEO', 'ZHNmNDNnYWRmZzQ1MjR2NDJmMjRm'
....
999, 'Joe', 'Doe', 'joe_doe@companyname.com', 'Software Engineer', 'ZHNmNDNnYWRmZzQ1MjR2NDJmMjRm' <-- Tampered with CEO salary
 ```


PoC
===============
The solution is to use authenticated encryption.
Demonstration and PoC of the solution may be found here: https://github.com/rusakovichma/authenticated-persistence-poc/blob/master/src/test/java/com/github/rusakovichma/persistance/authenticated/poc/controller/PersistenceSalaryTamperingAttack.java
```JAVA
        @Sql({"classpath:schema_h2_cleaning.sql", "classpath:schema_h2_salary_tampering.sql"})
        @Test
        public void testGetEmployee() {
            Employee ceoEmployee = restTemplate.getForObject(String.format("http://localhost:%d/employees/1", port), Employee.class);
            assertEquals(99999, (int) ceoEmployee.getSalary());
            assertNull(ceoEmployee.getSalaryEncrypted());
            assertNull(ceoEmployee.getNonce());

            ResponseEntity<Employee> devEmployee = restTemplate.getForEntity(String.format("http://localhost:%d/employees/999", port), Employee.class);
            //Tampering detected -> INTERNAL_SERVER_ERROR
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), devEmployee.getStatusCodeValue());
        }
```
