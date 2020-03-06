package com.maple.graphics.buffer.vertex;

public class VertexArray {
    private int mHandle;
    private int mVertexBufferHandle;
    private int mCount;

    public VertexArray(int handle, int vertexBufferHandle, int count) {
        mHandle = handle;
        mVertexBufferHandle = vertexBufferHandle;
        mCount = count;
    }

    public int getHandle() {
        return mHandle;
    }

    public int getVertexBufferHandle() {
        return mVertexBufferHandle;
    }

    public int getCount() {
        return mCount;
    }
}
