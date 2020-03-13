package com.maple.graphics.buffer.vertex;

import com.maple.graphics.buffer.vertex.format.VertexFormatBinder;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.*;

public class VertexArrayCreator {
    private VertexFormatBinder mVertexFormatBinder;
    private VertexBufferCreator mVertexBufferCreator;

    public VertexArrayCreator(VertexFormatBinder vertexFormatBinder,
                              VertexBufferCreator vertexBufferCreator) {
        mVertexFormatBinder = vertexFormatBinder;
        mVertexBufferCreator = vertexBufferCreator;
    }

    public VertexArray create(VertexBuffer... vertexBuffers) {
        int vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        int index = 0;
        int verticesCount = 0;
        for (VertexBuffer vertexBuffer : vertexBuffers) {
            if (verticesCount == 0) {
                verticesCount = vertexBuffer.getCount();
            }

            glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer.getHandle());
            mVertexFormatBinder.bind(index++, vertexBuffer.getFormat());
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }

        glBindVertexArray(0);

        return new VertexArray(vertexArray, vertexBuffers, verticesCount);
    }

    public void destroy(VertexArray vertexArray) {
        for (VertexBuffer vertexBuffer : vertexArray.getVertexBuffers()) {
            mVertexBufferCreator.destroy(vertexBuffer);
        }
        glDeleteVertexArrays(vertexArray.getHandle());
    }
}
