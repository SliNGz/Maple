package com.maple.graphics.buffer.vertex.format.element;

public abstract class VertexFormatElementFloatingPoint implements IVertexFormatElement {
    private int mCount;
    private int mType;
    private int mTypeSize;

    public VertexFormatElementFloatingPoint(int count, int type, int typeSize) {
        mCount = count;
        mType = type;
        mTypeSize = typeSize;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getType() {
        return mType;
    }

    @Override
    public int getTypeSize() {
        return mTypeSize;
    }

    @Override
    public boolean isNormalized() {
        return false;
    }
}
