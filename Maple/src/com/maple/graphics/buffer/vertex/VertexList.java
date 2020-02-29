package com.maple.graphics.buffer.vertex;

import com.maple.graphics.buffer.vertex.format.VertexFormat;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class VertexList<T> {
    private List<T> mVertices;
    private List<ByteBuffer> mVerticesData;
    private int mVerticesDataLength;

    public VertexList() {
        mVertices = new ArrayList<>();
        mVerticesData = new ArrayList<>();
        mVerticesDataLength = 0;
    }

    public T get(int index) {
        return mVertices.get(index);
    }

    public void add(T vertex) {
        mVertices.add(vertex);
        ByteBuffer vertexData = getVertexData(vertex);
        mVerticesData.add(vertexData);
        mVerticesDataLength += vertexData.remaining();
    }

    public int size() {
        return mVertices.size();
    }

    public abstract VertexFormat getVertexFormat();

    protected abstract ByteBuffer getVertexData(T vertex);

    public ByteBuffer generateDataBuffer() {
        ByteBuffer data = BufferUtils.createByteBuffer(mVerticesDataLength);
        for (ByteBuffer vertexData : mVerticesData) {
            data.put(vertexData);
        }
        data.flip();

        return data;
    }
}
