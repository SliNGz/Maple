package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public class VertexFormatElementFloat extends VertexFormatElementFloatingPoint {
    public VertexFormatElementFloat(int count) {
        super(count, OpenGLType.FLOAT);
    }
}
