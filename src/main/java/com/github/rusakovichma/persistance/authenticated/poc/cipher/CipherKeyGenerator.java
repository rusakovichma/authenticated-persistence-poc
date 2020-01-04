package com.github.rusakovichma.persistance.authenticated.poc.cipher;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public interface CipherKeyGenerator {

    public SecretKey generateKey() throws NoSuchAlgorithmException;

}
