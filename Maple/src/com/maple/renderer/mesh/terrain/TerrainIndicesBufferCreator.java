package com.maple.renderer.mesh.terrain;

import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.world.terrain.Terrain;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TerrainIndicesBufferCreator {
    public IndexBufferCreator mIndexBufferCreator;

    public TerrainIndicesBufferCreator(IndexBufferCreator indexBufferCreator) {
        mIndexBufferCreator = indexBufferCreator;
    }

    public IndexBuffer create(Terrain terrain) {
        int verticesCount = terrain.getWidth() * terrain.getLength();
        OpenGLType indicesType = getIndicesType(verticesCount);
        int indicesCount = (terrain.getWidth() - 1) * (terrain.getLength() - 1) * 6;

        ByteBuffer indicesByteBuffer = BufferUtils.createByteBuffer(indicesCount * indicesType.getSize());
        for (int x = 0; x < terrain.getWidth() - 1; ++x) {
            for (int z = 0; z < terrain.getLength() - 1; ++z) {
                List<Integer> quadIndices = getQuadIndices(terrain, x, z);
                addQuadIndices(indicesByteBuffer, quadIndices, indicesType);
            }
        }
        indicesByteBuffer.flip();

        return mIndexBufferCreator.create(indicesByteBuffer, BufferUsage.STATIC_DRAW, indicesType);
    }

    private OpenGLType getIndicesType(int verticesCount) {
        if (verticesCount <= Byte.MAX_VALUE + Math.abs(Byte.MIN_VALUE)) {
            return OpenGLType.UNSIGNED_BYTE;
        } else if (verticesCount <= Short.MAX_VALUE + Math.abs(Short.MIN_VALUE)) {
            return OpenGLType.UNSIGNED_SHORT;
        }

        return OpenGLType.UNSIGNED_INT;
    }

    private int getIndex(Terrain terrain, int x, int z) {
        return x + terrain.getWidth() * z;
    }

    private List<Integer> getQuadIndices(Terrain terrain, int x, int z) {
        List<Integer> indices = new ArrayList<>();

        indices.add(getIndex(terrain, x, z));
        indices.add(getIndex(terrain, x, z + 1));
        indices.add(getIndex(terrain, x + 1, z));

        indices.add(getIndex(terrain, x + 1, z + 1));
        indices.add(getIndex(terrain, x + 1, z));
        indices.add(getIndex(terrain, x, z + 1));

        return indices;
    }

    private void addQuadIndices(ByteBuffer indicesByteBuffer, List<Integer> quadIndices, OpenGLType indicesType) {
        for (int index : quadIndices) {
            if (indicesType == OpenGLType.UNSIGNED_BYTE) {
                indicesByteBuffer.put((byte) index);
            } else if (indicesType == OpenGLType.UNSIGNED_SHORT) {
                indicesByteBuffer.putShort((short) index);
            } else {
                indicesByteBuffer.putInt(index);
            }
        }
    }
}
