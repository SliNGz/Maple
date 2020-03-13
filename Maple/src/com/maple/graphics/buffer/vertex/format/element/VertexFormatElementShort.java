package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public class VertexFormatElementShort extends VertexFormatElementInteger {
    public VertexFormatElementShort(int count, boolean signed, boolean normalized) {
        super(count, signed, OpenGLType.SHORT, OpenGLType.UNSIGNED_SHORT, normalized);
    }
}
