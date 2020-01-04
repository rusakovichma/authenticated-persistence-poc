package com.github.rusakovichma.persistance.authenticated.poc.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class PlainText implements Serializable {

    private static final String CHARSET = "UTF-8";

    private final byte[] bytes;

    public PlainText(byte[] text) {
        this.bytes = text;
    }

    public PlainText(String input) {
        try {
            this.bytes = input.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlainText plainText = (PlainText) o;

        return Arrays.equals(bytes, plainText.bytes);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    public String getString() {
        try {
            return new String(bytes, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
