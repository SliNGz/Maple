package com.maple.renderer.mesh;

import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.specification.VertexPositionColor;
import com.maple.graphics.buffer.vertex.specification.VertexPositionColorList;
import com.maple.math.Vector3f;
import com.maple.utils.Color;
import com.maple.world.terrain.Terrain;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class TerrainMeshCreator {
    private VertexArrayCreator mVertexArrayCreator;
    private IndexBufferCreator mIndexBufferCreator;

    public TerrainMeshCreator(VertexArrayCreator vertexArrayCreator, IndexBufferCreator indexBufferCreator) {
        mVertexArrayCreator = vertexArrayCreator;
        mIndexBufferCreator = indexBufferCreator;
    }

    private int getIndicesType(int verticesCount) {
        if (verticesCount <= Byte.MAX_VALUE + Math.abs(Byte.MIN_VALUE)) {
            return GL_UNSIGNED_BYTE;
        } else if (verticesCount <= Short.MAX_VALUE + Math.abs(Short.MIN_VALUE)) {
            return GL_UNSIGNED_SHORT;
        }

        return GL_UNSIGNED_INT;
    }

    private int getIndicesTypeSize(int indicesType) {
        if (indicesType == GL_UNSIGNED_BYTE) {
            return Byte.BYTES;
        } else if (indicesType == GL_UNSIGNED_SHORT) {
            return Short.BYTES;
        }

        return Integer.BYTES;
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

    private void addQuadIndices(ByteBuffer indicesByteBuffer, List<Integer> quadIndices, int indicesType) {
        for (int index : quadIndices) {
            if (indicesType == GL_UNSIGNED_BYTE) {
                indicesByteBuffer.put((byte) index);
            } else if (indicesType == GL_UNSIGNED_SHORT) {
                indicesByteBuffer.putShort((short) index);
            } else {
                indicesByteBuffer.putInt(index);
            }
        }
    }

    private VertexArray createVertexArray(Terrain terrain) {
        VertexPositionColorList vertexPositionColorList = new VertexPositionColorList();
        for (int x = 0; x < terrain.getWidth(); ++x) {
            for (int z = 0; z < terrain.getLength(); ++z) {
                float y = terrain.get(x, z);
                vertexPositionColorList.add(new VertexPositionColor(new Vector3f(x, y, z),
                                                                    new Color(x / 512.0F, y / 64.0F, z / 512.0F, 1.0F)));
            }
        }

        return mVertexArrayCreator.create(vertexPositionColorList, GL_STATIC_DRAW);
    }

    private IndexBuffer createIndexBuffer(Terrain terrain) {
        int verticesCount = terrain.getWidth() * terrain.getLength();
        int indicesType = getIndicesType(verticesCount);
        int indicesTypeSize = getIndicesTypeSize(indicesType);
        int indicesCount = (terrain.getWidth() - 1) * (terrain.getLength() - 1) * 6;
        ByteBuffer indicesByteBuffer = BufferUtils.createByteBuffer(indicesCount * indicesTypeSize);
        for (int x = 0; x < terrain.getWidth() - 1; ++x) {
            for (int z = 0; z < terrain.getLength() - 1; ++z) {
                List<Integer> quadIndices = getQuadIndices(terrain, x, z);
                addQuadIndices(indicesByteBuffer, quadIndices, indicesType);
            }
        }
        indicesByteBuffer.flip();

        return mIndexBufferCreator.create(indicesByteBuffer, GL_STATIC_DRAW, indicesType);
    }

    public Mesh create(Terrain terrain) {
        return new Mesh(createVertexArray(terrain), createIndexBuffer(terrain));
    }
}
