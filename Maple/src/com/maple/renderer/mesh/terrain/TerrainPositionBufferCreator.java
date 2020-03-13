package com.maple.renderer.mesh.terrain;

import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.world.terrain.Terrain;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class TerrainPositionBufferCreator {
    private VertexBufferCreator mVertexBufferCreator;

    public TerrainPositionBufferCreator(VertexBufferCreator vertexBufferCreator) {
        mVertexBufferCreator = vertexBufferCreator;
    }

    public VertexBuffer create(Terrain terrain) {
        int bufferCapacity = terrain.getWidth() * terrain.getLength() * 3 * Float.BYTES;
        ByteBuffer buffer = BufferUtils.createByteBuffer(bufferCapacity);
        for (int x = 0; x < terrain.getWidth(); ++x) {
            for (int z = 0; z < terrain.getLength(); ++z) {
                float y = terrain.get(x, z);
                buffer.putFloat(x);
                buffer.putFloat(y);
                buffer.putFloat(z);
            }
        }
        buffer.flip();

        return mVertexBufferCreator.create(buffer, BufferUsage.STATIC_DRAW, VertexFormat.POSITION_BUFFER);
    }
}
