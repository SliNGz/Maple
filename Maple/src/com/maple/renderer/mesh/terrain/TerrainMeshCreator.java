package com.maple.renderer.mesh.terrain;

import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.renderer.mesh.Mesh;
import com.maple.world.terrain.Terrain;

public class TerrainMeshCreator {
    private VertexArrayCreator mVertexArrayCreator;
    private TerrainPositionBufferCreator mPositionBufferCreator;
    private TerrainIndicesBufferCreator mIndicesBufferCreator;

    public TerrainMeshCreator(VertexArrayCreator vertexArrayCreator,
                              TerrainPositionBufferCreator positionBufferCreator,
                              TerrainIndicesBufferCreator indicesBufferCreator) {
        mVertexArrayCreator = vertexArrayCreator;
        mPositionBufferCreator = positionBufferCreator;
        mIndicesBufferCreator = indicesBufferCreator;
    }

    public Mesh create(Terrain terrain, VertexBuffer... additionalVertexBuffers) {
        VertexBuffer[] vertexBuffers = new VertexBuffer[additionalVertexBuffers.length + 1];
        vertexBuffers[0] = mPositionBufferCreator.create(terrain);
        System.arraycopy(additionalVertexBuffers, 0, vertexBuffers, 1, additionalVertexBuffers.length);
        VertexArray vertexArray = mVertexArrayCreator.create(vertexBuffers);

        IndexBuffer indexBuffer = mIndicesBufferCreator.create(terrain);

        return new Mesh(vertexArray, indexBuffer);
    }
}
