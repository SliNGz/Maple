package com.maple.graphics.buffer.vertex;

import com.maple.graphics.buffer.vertex.format.VertexFormatBinder;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class VertexArrayCreator {
    private VertexFormatBinder mVertexFormatBinder;

    public VertexArrayCreator(VertexFormatBinder vertexFormatBinder) {
        mVertexFormatBinder = vertexFormatBinder;
    }

    public <T> VertexArray create(VertexList<T> vertices, int usage) {
        int vertexArray = glGenVertexArrays();
        glBindVertexArray(vertexArray);

        int vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices.generateDataBuffer(), usage);
        mVertexFormatBinder.bind(vertices.getVertexFormat());
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        return new VertexArray(vertexArray, vertexBuffer, vertices.size());
    }

    public void destroy(VertexArray vertexArray) {
        glDeleteBuffers(vertexArray.getVertexBufferHandle());
        glDeleteVertexArrays(vertexArray.getHandle());
    }
}
