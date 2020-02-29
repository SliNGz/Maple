package com.maple.graphics.buffer.vertex.format.element;

public interface IVertexFormatElement {
    int getCount();

    int getType();

    int getTypeSize();

    boolean isNormalized();

    default int getSize() {
        return getTypeSize() * getCount();
    }
}
