package com.github.rusakovichma.persistance.authenticated.poc.model;

import java.io.Serializable;

/**
 * Contains encrypted data and nonce.
 *
 * Returning EncryptedData means that users are not tempted to reuse a nonce, and it is
 * neatly returned with the data.
 */

public class EncryptedData implements Serializable {

    private final Nonce nonce;
    private final CipherText cipherText;

    public EncryptedData(Nonce nonce, CipherText cipherText) {
        this.nonce = nonce;
        this.cipherText = cipherText;
    }

    public CipherText getCipherText() {
        return cipherText;
    }

    public Nonce getNonce() {
        return nonce;
    }

}
