package com.maple.utils;

import com.maple.math.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class ByteBufferUtils {
    public static ByteBuffer extract(Vector3f vector3f) {
        return BufferUtils.createByteBuffer(3 * Float.BYTES)
                          .putFloat(vector3f.X)
                          .putFloat(vector3f.Y)
                          .putFloat(vector3f.Z)
                          .flip();
    }

    public static ByteBuffer extract(Color color) {
        return BufferUtils.createByteBuffer(4)
                          .put((byte) (color.getR() & 0xFF))
                          .put((byte) (color.getG() & 0xFF))
                          .put((byte) (color.getB() & 0xFF))
                          .put((byte) (color.getA() & 0xFF))
                          .flip();
    }

    public static ByteBuffer merge(int capacity, ByteBuffer... byteBuffers) {
        ByteBuffer result = BufferUtils.createByteBuffer(capacity);
        for (ByteBuffer byteBuffer : byteBuffers) {
            result.put(byteBuffer);
        }
        result.flip();

        return result;
    }

    public static ByteBuffer merge(ByteBuffer... byteBuffers) {
        int capacity = 0;
        for (ByteBuffer byteBuffer : byteBuffers) {
            capacity += byteBuffer.remaining();
        }

        return merge(capacity, byteBuffers);
    }
}
