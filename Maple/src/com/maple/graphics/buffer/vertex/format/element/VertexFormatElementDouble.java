package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public class VertexFormatElementDouble extends VertexFormatElementFloatingPoint {
    public VertexFormatElementDouble(int count) {
        super(count, OpenGLType.DOUBLE);
    }
}
