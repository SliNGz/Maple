package com.maple.graphics.buffer.vertex.format.element;

import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;

public class VertexFormatElementInt extends VertexFormatElementInteger {
    public VertexFormatElementInt(int count, boolean signed, boolean normalized) {
        super(count, signed, GL_INT, GL_UNSIGNED_INT, Integer.BYTES, normalized);
    }
}
