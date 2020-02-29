package com.maple.graphics.buffer.vertex.format.element;

import static org.lwjgl.opengl.GL11.GL_FLOAT;

public class VertexFormatElementFloat extends VertexFormatElementFloatingPoint {
    public VertexFormatElementFloat(int count) {
        super(count, GL_FLOAT, Float.BYTES);
    }
}
