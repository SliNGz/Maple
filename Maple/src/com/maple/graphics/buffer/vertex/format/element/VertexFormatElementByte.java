package com.maple.graphics.buffer.vertex.format.element;

import static org.lwjgl.opengl.GL11.GL_BYTE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;

public class VertexFormatElementByte extends VertexFormatElementInteger {
    public VertexFormatElementByte(int count, boolean signed, boolean normalized) {
        super(count, signed, GL_BYTE, GL_UNSIGNED_BYTE, Byte.BYTES, normalized);
    }
}
