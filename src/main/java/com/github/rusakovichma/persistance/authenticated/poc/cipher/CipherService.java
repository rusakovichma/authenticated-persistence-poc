package com.github.rusakovichma.persistance.authenticated.poc.cipher;

import com.github.rusakovichma.persistance.authenticated.poc.model.AuthenticationTag;
import com.github.rusakovichma.persistance.authenticated.poc.model.EncryptedData;
import com.github.rusakovichma.persistance.authenticated.poc.model.PlainText;

import java.security.GeneralSecurityException;

public interface CipherService {

    public EncryptedData encrypt(PlainText plainText, AuthenticationTag authTag) throws GeneralSecurityException;

    public PlainText decrypt(EncryptedData encryptedData, AuthenticationTag authTag) throws GeneralSecurityException;

}
