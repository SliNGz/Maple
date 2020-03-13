package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public class VertexFormatElementByte extends VertexFormatElementInteger {
    public VertexFormatElementByte(int count, boolean signed, boolean normalized) {
        super(count, signed, OpenGLType.BYTE, OpenGLType.UNSIGNED_BYTE, normalized);
    }
}
