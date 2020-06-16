package com.maple.renderer.mesh.quad;

import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.buffer.vertex.specification.VertexPositionTexture;
import com.maple.math.Vector2f;
import com.maple.math.Vector3f;
import com.maple.renderer.mesh.Mesh;

import java.util.List;

public class PositionTextureQuadMeshCreator {
    private VertexBufferCreator mVertexBufferCreator;
    private VertexArrayCreator mVertexArrayCreator;
    private IndexBufferCreator mIndexBufferCreator;

    public PositionTextureQuadMeshCreator(VertexBufferCreator vertexBufferCreator, VertexArrayCreator vertexArrayCreator,
                                          IndexBufferCreator indexBufferCreator) {
        mVertexBufferCreator = vertexBufferCreator;
        mVertexArrayCreator = vertexArrayCreator;
        mIndexBufferCreator = indexBufferCreator;
    }

    public Mesh create() {
        List<VertexPositionTexture> vertices = List.of(
                new VertexPositionTexture(new Vector3f(0, 0, 0), new Vector2f(0, 0)),
                new VertexPositionTexture(new Vector3f(0, 1, 0), new Vector2f(0, 1)),
                new VertexPositionTexture(new Vector3f(1, 1, 0), new Vector2f(1, 1)),
                new VertexPositionTexture(new Vector3f(1, 0, 0), new Vector2f(1, 0)));
        VertexBuffer vertexBuffer = mVertexBufferCreator.create(vertices, BufferUsage.STATIC_DRAW);

        VertexArray vertexArray = mVertexArrayCreator.create(vertexBuffer);
        IndexBuffer indexBuffer = mIndexBufferCreator.create(new int[]{0, 1, 2, 2, 3, 0}, BufferUsage.STATIC_DRAW);

        return new Mesh(vertexArray, indexBuffer);
    }
}
