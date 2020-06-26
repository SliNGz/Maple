package com.maple.content.loaders.model;

import com.maple.content.loaders.model.exceptions.NoVertexDataException;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import org.lwjgl.assimp.AIMesh;

public interface IAssimpMeshVertexBufferExtractor {
    VertexBuffer extract(AIMesh aiMesh) throws NoVertexDataException;
}
