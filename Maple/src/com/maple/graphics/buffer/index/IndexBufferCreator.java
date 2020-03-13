package com.maple.graphics.buffer.index;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferUsage;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL15.*;

public class IndexBufferCreator {
    public IndexBuffer create(byte[] indices, BufferUsage usage) {
        ByteBuffer indicesByteBuffer = BufferUtils.createByteBuffer(indices.length);
        indicesByteBuffer.put(indices);
        indicesByteBuffer.flip();

        return create(indicesByteBuffer, usage, OpenGLType.UNSIGNED_BYTE);
    }

    public IndexBuffer create(short[] indices, BufferUsage usage) {
        ByteBuffer indicesByteBuffer = BufferUtils.createByteBuffer(Short.BYTES * indices.length);
        ShortBuffer indicesShortBuffer = indicesByteBuffer.asShortBuffer();
        indicesShortBuffer.put(indices);
        indicesShortBuffer.flip();

        return create(indicesByteBuffer, usage, OpenGLType.UNSIGNED_SHORT);
    }

    public IndexBuffer create(int[] indices, BufferUsage usage) {
        ByteBuffer indicesByteBuffer = BufferUtils.createByteBuffer(Integer.BYTES * indices.length);
        IntBuffer indicesIntBuffer = indicesByteBuffer.asIntBuffer();
        indicesIntBuffer.put(indices);
        indicesIntBuffer.flip();

        return create(indicesByteBuffer, usage, OpenGLType.UNSIGNED_INT);
    }

    public IndexBuffer create(ByteBuffer indices, BufferUsage usage, OpenGLType type) {
        int indexBuffer = glGenBuffers();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, usage.getValue());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        return new IndexBuffer(indexBuffer, type, indices.remaining());
    }

    public void destroy(IndexBuffer indexBuffer) {
        glDeleteBuffers(indexBuffer.getHandle());
    }
}
