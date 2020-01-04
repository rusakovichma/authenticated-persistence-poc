package com.github.rusakovichma.persistance.authenticated.poc.model;

import java.io.Serializable;

public class Nonce implements Serializable {

    private final byte[] bytes;

    public Nonce(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

}
