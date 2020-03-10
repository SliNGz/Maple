package com.maple.renderer.mesh;

import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.vertex.VertexArray;

public class Mesh {
    private VertexArray mVertexArray;
    private IndexBuffer mIndexBuffer;

    public Mesh(VertexArray vertexArray, IndexBuffer indexBuffer) {
        mVertexArray = vertexArray;
        mIndexBuffer = indexBuffer;
    }

    public VertexArray getVertexArray() {
        return mVertexArray;
    }

    public IndexBuffer getIndexBuffer() {
        return mIndexBuffer;
    }
}
