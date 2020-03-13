package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public class VertexFormatElementInt extends VertexFormatElementInteger {
    public VertexFormatElementInt(int count, boolean signed, boolean normalized) {
        super(count, signed, OpenGLType.INT, OpenGLType.UNSIGNED_INT, normalized);
    }
}
