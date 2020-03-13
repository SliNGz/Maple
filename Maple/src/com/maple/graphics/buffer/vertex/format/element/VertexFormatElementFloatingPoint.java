package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public abstract class VertexFormatElementFloatingPoint implements IVertexFormatElement {
    private int mCount;
    private OpenGLType mType;

    public VertexFormatElementFloatingPoint(int count, OpenGLType type) {
        mCount = count;
        mType = type;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public OpenGLType getType() {
        return mType;
    }

    @Override
    public boolean isNormalized() {
        return false;
    }
}
