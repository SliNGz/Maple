package com.maple.graphics.buffer.vertex.format.element;

import com.maple.graphics.OpenGLType;

public abstract class VertexFormatElementInteger implements IVertexFormatElement {
    private int mCount;
    private boolean mSigned;
    private OpenGLType mSignedType;
    private OpenGLType mUnsignedType;
    private boolean mNormalized;

    public VertexFormatElementInteger(int count,
                                      boolean signed, OpenGLType signedType, OpenGLType unsignedType,
                                      boolean normalized) {
        mCount = count;
        mSigned = signed;
        mSignedType = signedType;
        mUnsignedType = unsignedType;
        mNormalized = normalized;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public OpenGLType getType() {
        return mSigned ? mSignedType : mUnsignedType;
    }

    @Override
    public boolean isNormalized() {
        return mNormalized;
    }
}
