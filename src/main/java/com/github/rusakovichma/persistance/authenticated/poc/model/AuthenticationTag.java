package com.github.rusakovichma.persistance.authenticated.poc.model;

import java.io.Serializable;

/**
 * AAD is optional clear text. Only the integrity of AAD is assured.
 * A typical use case for additional data is to store protocol-specific metadata about
 * the message, such as its length and encoding, sequence number etc.
 *
 * http://crypto.stackexchange.com/a/15701/10979
 */
public class AuthenticationTag implements Serializable {

    private final byte[] data;

    public AuthenticationTag(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

}
