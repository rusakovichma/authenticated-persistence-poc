package com.github.rusakovichma.persistance.authenticated.poc.util;

import java.nio.ByteBuffer;

public class BytesUtil {

    private BytesUtil() {
    }

    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4)
                .putInt(value)
                .array();
    }

    public static int intFromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes)
                .getInt();
    }

    public static byte[] longToByteArray(long x) {
        return ByteBuffer.allocate(Long.BYTES)
                .putLong(0, x)
                .array();
    }

    public static long byteArrayToLong(byte[] bytes) {
        return ByteBuffer.allocate(Long.BYTES)
                .put(bytes, 0, bytes.length)
                .flip()
                .getLong();
    }

}
