package com.github.rusakovichma.persistance.authenticated.poc.cipher.javax;

import com.github.rusakovichma.persistance.authenticated.poc.cipher.CipherKeyGenerator;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class ConstantCipherKey implements CipherKeyGenerator {

    private final byte[] secretKeyBytes;
    private final String alg;

    public ConstantCipherKey(byte[] secretKeyBytes, String alg) {
        this.secretKeyBytes = secretKeyBytes;
        this.alg = alg;
    }

    @Override
    public SecretKey generateKey() throws NoSuchAlgorithmException {
        return new SecretKeySpec(secretKeyBytes, alg);
    }
}
