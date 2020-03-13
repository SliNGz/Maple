package com.maple.graphics.buffer.vertex;

import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.graphics.buffer.vertex.specification.IVertex;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;

public class VertexBufferCreator {
    public VertexBuffer create(ByteBuffer data, BufferUsage usage, VertexFormat vertexFormat) {
        int handle = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, handle);
        glBufferData(GL_ARRAY_BUFFER, data, usage.getValue());
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        int count = data.remaining() / vertexFormat.getStride();

        return new VertexBuffer(handle, count, vertexFormat);
    }

    public <T extends IVertex> VertexBuffer create(List<T> vertices, BufferUsage usage) {
        IVertex randomVertex = vertices.get(0);
        VertexFormat vertexFormat = randomVertex.getVertexFormat();
        ByteBuffer verticesData = BufferUtils.createByteBuffer(vertices.size() * vertexFormat.getStride());

        for (T vertex : vertices) {
            for (ByteBuffer vertexDataBuffer : vertex.getDataBuffers()) {
                verticesData.put(vertexDataBuffer);
            }
        }
        verticesData.flip();

        return create(verticesData, usage, vertexFormat);
    }

    public void destroy(VertexBuffer vertexBuffer) {
        glDeleteBuffers(vertexBuffer.getHandle());
    }
}
