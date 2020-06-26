package com.maple.content.loaders.model;

import com.maple.content.loaders.model.exceptions.NoVertexDataException;
import com.maple.graphics.OpenGLType;
import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.renderer.mesh.Mesh;
import com.maple.utils.IndexBufferUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class AssimpMeshConverter {
    private VertexArrayCreator mVertexArrayCreator;
    private IndexBufferCreator mIndexBufferCreator;

    private List<IAssimpMeshVertexBufferExtractor> mVertexBufferExtractors;

    public AssimpMeshConverter(VertexArrayCreator vertexArrayCreator,
                               VertexBufferCreator vertexBufferCreator,
                               IndexBufferCreator indexBufferCreator) {
        mVertexArrayCreator = vertexArrayCreator;
        mIndexBufferCreator = indexBufferCreator;
        mVertexBufferExtractors = new ArrayList<>();
        mVertexBufferExtractors.add(new AssimpMeshPositionBufferExtractor(vertexBufferCreator));
    }

    public Mesh convert(AIMesh aiMesh) {
        VertexBuffer[] vertexBuffers = getVertexBuffers(aiMesh);
        VertexArray vertexArray = mVertexArrayCreator.create(vertexBuffers);
        IndexBuffer indexBuffer = getIndexBuffer(aiMesh);

        return new Mesh(vertexArray, indexBuffer);
    }

    private VertexBuffer[] getVertexBuffers(AIMesh aiMesh) {
        List<VertexBuffer> vertexBuffers = new ArrayList<>();
        for (IAssimpMeshVertexBufferExtractor assimpMeshVertexBufferExtractor : mVertexBufferExtractors) {
            try {
                VertexBuffer vertexBuffer = assimpMeshVertexBufferExtractor.extract(aiMesh);
                vertexBuffers.add(vertexBuffer);
            } catch (NoVertexDataException ignored) {
            }
        }

        return vertexBuffers.toArray(VertexBuffer[]::new);
    }

    private IndexBuffer getIndexBuffer(AIMesh aiMesh) {
        AIFace.Buffer aiFaces = aiMesh.mFaces();

        int indicesAmount = aiMesh.mNumFaces() * aiFaces.mNumIndices();
        OpenGLType indicesType = IndexBufferUtils.getIndicesType(aiMesh.mNumVertices());

        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indicesAmount * indicesType.getSize());
        while (aiFaces.hasRemaining()) {
            AIFace aiFace = aiFaces.get();
            IntBuffer aiIndices = aiFace.mIndices();
            while (aiIndices.hasRemaining()) {
                int index = aiIndices.get();

                if (indicesType == OpenGLType.UNSIGNED_BYTE) {
                    indicesBuffer.put((byte) index);
                } else if (indicesType == OpenGLType.UNSIGNED_SHORT) {
                    indicesBuffer.putShort((short) index);
                } else {
                    indicesBuffer.putInt(index);
                }
            }
        }
        indicesBuffer.flip();

        return mIndexBufferCreator.create(indicesBuffer, BufferUsage.STATIC_DRAW, indicesType);
    }

    public VertexArrayCreator getVertexArrayCreator() {
        return mVertexArrayCreator;
    }

    public IndexBufferCreator getIndexBufferCreator() {
        return mIndexBufferCreator;
    }
}