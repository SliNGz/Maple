package com.maple.renderer.mesh.terrain;

import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.buffer.vertex.format.VertexFormat;
import com.maple.utils.ByteBufferUtils;
import com.maple.world.terrain.Terrain;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

public class TerrainColorBufferCreator {
    private VertexBufferCreator mVertexBufferCreator;
    private ITerrainColorizer mTerrainColorizer;

    public TerrainColorBufferCreator(VertexBufferCreator vertexBufferCreator,
                                     ITerrainColorizer terrainColorizer) {
        mVertexBufferCreator = vertexBufferCreator;
        mTerrainColorizer = terrainColorizer;
    }

    public VertexBuffer create(Terrain terrain) {
        int bufferCapacity = terrain.getWidth() * terrain.getLength() * 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(bufferCapacity);
        for (int x = 0; x < terrain.getWidth(); ++x) {
            for (int z = 0; z < terrain.getLength(); ++z) {
                float y = terrain.get(x, z);
                ByteBuffer colorData = ByteBufferUtils.extract(mTerrainColorizer.getColorAt(x, y, z));
                buffer.put(colorData);
            }
        }
        buffer.flip();

        return mVertexBufferCreator.create(buffer, BufferUsage.STATIC_DRAW, VertexFormat.COLOR_BUFFER);
    }
}
