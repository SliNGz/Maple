package com.maple.graphics.buffer.vertex.format.element;

public abstract class VertexFormatElementInteger implements IVertexFormatElement {
    private int mCount;
    private boolean mSigned;
    private int mSignedType;
    private int mUnsignedType;
    private int mTypeSize;
    private boolean mNormalized;

    public VertexFormatElementInteger(int count,
                                      boolean signed, int signedType, int unsignedType, int typeSize,
                                      boolean normalized) {
        mCount = count;
        mSigned = signed;
        mSignedType = signedType;
        mUnsignedType = unsignedType;
        mTypeSize = typeSize;
        mNormalized = normalized;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public int getType() {
        return mSigned ? mSignedType : mUnsignedType;
    }

    @Override
    public int getTypeSize() {
        return mTypeSize;
    }

    @Override
    public boolean isNormalized() {
        return mNormalized;
    }
}
