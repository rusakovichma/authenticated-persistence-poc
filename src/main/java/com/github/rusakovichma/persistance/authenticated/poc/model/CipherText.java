package com.github.rusakovichma.persistance.authenticated.poc.model;

import java.io.Serializable;

public class CipherText implements Serializable {
    
    private final byte[] bytes;

    public CipherText(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
