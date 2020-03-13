package com.maple.graphics.buffer.vertex;

import com.maple.graphics.buffer.vertex.format.VertexFormat;

public class VertexBuffer {
    private int mHandle;
    private int mCount;
    private VertexFormat mFormat;

    public VertexBuffer(int handle, int count, VertexFormat format) {
        mHandle = handle;
        mCount = count;
        mFormat = format;
    }

    public int getHandle() {
        return mHandle;
    }

    public int getCount() {
        return mCount;
    }

    public VertexFormat getFormat() {
        return mFormat;
    }
}
