package com.maple.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtils {
    public static FloatBuffer createDirectFloatBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity * Float.BYTES)
                         .order(ByteOrder.nativeOrder())
                         .asFloatBuffer()
                         .rewind();
    }
}
