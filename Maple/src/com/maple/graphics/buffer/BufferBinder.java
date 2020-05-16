package com.maple.graphics.buffer;

import com.maple.graphics.buffer.exceptions.NoBoundIndexBufferException;
import com.maple.graphics.buffer.exceptions.NoBoundVertexArrayException;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class BufferBinder {
    private VertexArray mVertexArray;
    private IndexBuffer mIndexBuffer;

    public BufferBinder() {
        mVertexArray = null;
        mIndexBuffer = null;
    }

    public void bindVertexArray(VertexArray vertexArray) {
        if (mVertexArray != vertexArray) {
            glBindVertexArray(vertexArray.getHandle());
            mVertexArray = vertexArray;
        }
    }

    public void unbindVertexArray() {
        if (mVertexArray != null) {
            glBindVertexArray(0);
            mVertexArray = null;
        }
    }

    public void bindIndexBuffer(IndexBuffer indexBuffer) {
        if (mIndexBuffer != indexBuffer) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer.getHandle());
            mIndexBuffer = indexBuffer;
        }
    }

    public void unbindIndexBuffer() {
        if (mIndexBuffer != null) {
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
            mIndexBuffer = null;
        }
    }

    public VertexArray getBoundVertexArray() throws NoBoundVertexArrayException {
        if (mVertexArray == null) {
            throw new NoBoundVertexArrayException();
        }

        return mVertexArray;
    }

    public IndexBuffer getBoundIndexBuffer() throws NoBoundIndexBufferException {
        if (mIndexBuffer == null) {
            throw new NoBoundIndexBufferException();
        }

        return mIndexBuffer;
    }
}
