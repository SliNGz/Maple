package com.maple.content.loaders.model;

import com.maple.content.loaders.model.exceptions.NoVertexDataException;
import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.buffer.vertex.format.VertexFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import java.nio.ByteBuffer;

public class AssimpMeshPositionBufferExtractor implements IAssimpMeshVertexBufferExtractor {
    private VertexBufferCreator mVertexBufferCreator;

    public AssimpMeshPositionBufferExtractor(VertexBufferCreator vertexBufferCreator) {
        mVertexBufferCreator = vertexBufferCreator;
    }

    @Override
    public VertexBuffer extract(AIMesh aiMesh) throws NoVertexDataException {
        if (aiMesh.mNumVertices() == 0) {
            throw new NoVertexDataException();
        }

        AIVector3D.Buffer aiVertices = aiMesh.mVertices();

        int dataSize = aiMesh.mNumVertices() * 3 * Float.BYTES;
        ByteBuffer data = BufferUtils.createByteBuffer(dataSize);
        while (aiVertices.hasRemaining()) {
            AIVector3D aiVertex = aiVertices.get();
            data.putFloat(aiVertex.x());
            data.putFloat(aiVertex.y());
            data.putFloat(aiVertex.z());
        }
        data.flip();

        return mVertexBufferCreator.create(data, BufferUsage.STATIC_DRAW, VertexFormat.POSITION_BUFFER);
    }
}
