package com.maple.graphics.buffer.index;

public class IndexBuffer {
    private int mHandle;
    private int mType;
    private int mCount;

    public IndexBuffer(int handle, int type, int count) {
        mHandle = handle;
        mType = type;
        mCount = count;
    }

    public int getHandle() {
        return mHandle;
    }

    public int getType() {
        return mType;
    }

    public int getCount() {
        return mCount;
    }
}
