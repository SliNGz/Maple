package com.maple.utils;

import com.maple.graphics.OpenGLType;

public class IndexBufferUtils {
    public static OpenGLType getIndicesType(int verticesCount) {
        if (verticesCount <= Byte.MAX_VALUE + Math.abs(Byte.MIN_VALUE)) {
            return OpenGLType.UNSIGNED_BYTE;
        } else if (verticesCount <= Short.MAX_VALUE + Math.abs(Short.MIN_VALUE)) {
            return OpenGLType.UNSIGNED_SHORT;
        }

        return OpenGLType.UNSIGNED_INT;
    }
}
