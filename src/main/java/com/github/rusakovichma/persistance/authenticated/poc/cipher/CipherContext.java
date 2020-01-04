package com.github.rusakovichma.persistance.authenticated.poc.cipher;

import com.github.rusakovichma.persistance.authenticated.poc.model.Nonce;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.AlgorithmParameters;

public interface CipherContext {

    public Nonce generateNonce(int length);

    public SecretKey getSecretKey(Cipher cipher, AlgorithmParameters params);

}
