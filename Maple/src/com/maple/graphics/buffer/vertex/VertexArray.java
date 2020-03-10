package com.maple.graphics.buffer.vertex;

public class VertexArray {
    private int mHandle;
    private int mVertexBufferHandle;
    private int mVerticesCount;

    public VertexArray(int handle, int vertexBufferHandle, int verticesCount) {
        mHandle = handle;
        mVertexBufferHandle = vertexBufferHandle;
        mVerticesCount = verticesCount;
    }

    public int getHandle() {
        return mHandle;
    }

    public int getVertexBufferHandle() {
        return mVertexBufferHandle;
    }

    public int getVerticesCount() {
        return mVerticesCount;
    }
}
