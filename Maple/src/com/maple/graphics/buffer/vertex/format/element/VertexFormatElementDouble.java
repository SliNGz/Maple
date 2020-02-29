package com.maple.graphics.buffer.vertex.format.element;

import static org.lwjgl.opengl.GL11.GL_DOUBLE;

public class VertexFormatElementDouble extends VertexFormatElementFloatingPoint {
    public VertexFormatElementDouble(int count) {
        super(count, GL_DOUBLE, Double.BYTES);
    }
}
