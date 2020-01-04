package com.github.rusakovichma.persistance.authenticated.poc.service.impl;

import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherService;
import com.github.rusakovichma.persistance.authenticated.poc.model.*;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeCreateException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeDecryptionException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeEncryptionException;
import com.github.rusakovichma.persistance.authenticated.poc.service.exception.EmployeeNotFoundException;
import com.github.rusakovichma.persistance.authenticated.poc.repository.EmployeesRepository;
import com.github.rusakovichma.persistance.authenticated.poc.service.EmployeeService;
import com.github.rusakovichma.persistance.authenticated.poc.util.BytesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private CipherService cipherService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void createEmployee(Employee employee) throws EmployeeCreateException, EmployeeEncryptionException {
        try {
            employee.setSalaryEncrypted("");
            employee.setNonce("");

            final Employee employeePersisted = employeesRepository.save(employee);

            try {
                final EncryptedData encryptedData = cipherService.encrypt(
                        new PlainText(BytesUtil.intToByteArray(employeePersisted.getSalary())),
                        new AuthenticationTag(BytesUtil.longToByteArray(employeePersisted.getId()))
                );

                employeePersisted.setSalaryEncrypted(Base64.getEncoder().encodeToString(encryptedData.getCipherText().getBytes()));
                employeePersisted.setNonce(Base64.getEncoder().encodeToString(encryptedData.getNonce().getBytes()));
                //null the salary unencrypted value in memory
                employeePersisted.setSalary(null);
            } catch (Exception ex) {
                throw new EmployeeEncryptionException(ex);
            }

            employeesRepository.update(employeePersisted);
        } catch (EmployeeEncryptionException eeex) {
            throw eeex;
        } catch (Exception ex) {
            throw new EmployeeCreateException(ex);
        }
    }

    @Override
    public Employee findEmployee(Long id) throws EmployeeNotFoundException, EmployeeDecryptionException {
        try {
            Employee employee = employeesRepository.getById(id);
            if (employee == null) {
                throw new EmployeeNotFoundException();
            }

            try {
                EncryptedData encryptedData = new EncryptedData(
                        new Nonce(Base64.getDecoder().decode(employee.getNonce())),
                        new CipherText(Base64.getDecoder().decode(employee.getSalaryEncrypted()))
                );
                PlainText salaryUnencrypted = cipherService.decrypt(encryptedData, new AuthenticationTag(BytesUtil.longToByteArray(employee.getId())));
                employee.setSalary(BytesUtil.intFromByteArray(salaryUnencrypted.getBytes()));
            } catch (Exception ex) {
                throw new EmployeeDecryptionException(ex);
            }

            //clear encrypted values
            employee.setSalaryEncrypted(null);
            employee.setNonce(null);

            return employee;
        } catch (EmployeeDecryptionException ede) {
            throw ede;
        } catch (EmployeeNotFoundException enfex) {
            throw enfex;
        } catch (Exception ex) {
            throw new EmployeeNotFoundException(ex);
        }
    }
}
