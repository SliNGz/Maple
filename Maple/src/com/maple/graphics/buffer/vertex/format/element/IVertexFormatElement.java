package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public interface IVertexFormatElement {
    int getCount();

    OpenGLType getType();

    boolean isNormalized();

    default int getSize() {
        OpenGLType type = getType();
        return type.getSize() * getCount();
    }
}
