package com.maple.graphics.buffer.vertex;

import com.maple.graphics.buffer.vertex.format.VertexFormatBinder;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArray {
    private int mHandle;
    private int mVertexBufferHandle;
    private int mCount;

    private VertexArray(int handle, int vertexBufferHandle, int count) {
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

    public static <T> VertexArray create(VertexList<T> vertices, int usage) {
        int vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices.generateDataBuffer(), usage);
        VertexFormatBinder.bind(vertices.getVertexFormat());
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        return new VertexArray(vertexArray, vertexBuffer, vertices.size());
    }

    public static void destroy(VertexArray vertexArray) {
        glDeleteBuffers(vertexArray.getVertexBufferHandle());
        glDeleteVertexArrays(vertexArray.getHandle());
    }
}
