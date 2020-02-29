package com.maple.graphics.buffer.vertex.format.element;

import static org.lwjgl.opengl.GL11.GL_SHORT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;

public class VertexFormatElementShort extends VertexFormatElementInteger {
    public VertexFormatElementShort(int count, boolean signed, boolean normalized) {
        super(count, signed, GL_SHORT, GL_UNSIGNED_SHORT, Short.BYTES, normalized);
    }
}
