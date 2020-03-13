package com.maple.graphics.buffer.index;

import com.maple.graphics.OpenGLType;

public class IndexBuffer {
    private int mHandle;
    private OpenGLType mType;
    private int mCount;

    public IndexBuffer(int handle, OpenGLType type, int count) {
        mHandle = handle;
        mType = type;
        mCount = count;
    }

    public int getHandle() {
        return mHandle;
    }

    public OpenGLType getType() {
        return mType;
    }

    public int getCount() {
        return mCount;
    }
}
