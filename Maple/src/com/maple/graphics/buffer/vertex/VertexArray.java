package com.maple.graphics.buffer.vertex;

public class VertexArray {
    private int mHandle;
    private VertexBuffer[] mVertexBuffers;
    private int mVerticesCount;

    public VertexArray(int handle, VertexBuffer[] vertexBuffers, int verticesCount) {
        mHandle = handle;
        mVertexBuffers = vertexBuffers;
        mVerticesCount = verticesCount;
    }

    public int getHandle() {
        return mHandle;
    }

    public VertexBuffer[] getVertexBuffers() {
        return mVertexBuffers;
    }

    public int getVerticesCount() {
        return mVerticesCount;
    }
}
