package com.maple.content.loaders.model;

import com.maple.content.IContentLoader;
import com.maple.content.loaders.model.exceptions.AssimpImportFileFailedException;
import com.maple.content.loaders.model.exceptions.ModelLoadFailedException;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.log.Logger;
import com.maple.math.Matrix4f;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.model.Model;
import com.maple.renderer.model.ModelMesh;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import java.util.ArrayList;
import java.util.List;

public class ModelLoader implements IContentLoader<Model> {
    private AssimpMeshConverter mAssimpMeshConverter;
    private VertexArrayCreator mVertexArrayCreator;
    private IndexBufferCreator mIndexBufferCreator;

    public ModelLoader(AssimpMeshConverter assimpMeshConverter) {
        mAssimpMeshConverter = assimpMeshConverter;
        mVertexArrayCreator = mAssimpMeshConverter.getVertexArrayCreator();
        mIndexBufferCreator = mAssimpMeshConverter.getIndexBufferCreator();
    }

    @Override
    public Model load(String path) {
        try {
            List<ModelMesh> meshes = new ArrayList<>();

            int flags = Assimp.aiProcess_JoinIdenticalVertices |
                        Assimp.aiProcess_Triangulate |
                        Assimp.aiProcess_JoinIdenticalVertices;
            AIScene aiScene = Assimp.aiImportFile(path, flags);
            if (aiScene == null) {
                throw new AssimpImportFileFailedException(Assimp.aiGetErrorString());
            }

            PointerBuffer aiMeshes = aiScene.mMeshes();
            int meshCount = aiScene.mNumMeshes();
            while (aiMeshes.hasRemaining()) {
                AIMesh aiMesh = AIMesh.create(aiMeshes.get());
                Mesh mesh = mAssimpMeshConverter.convert(aiMesh);
                Matrix4f modelMatrix = Matrix4f.createIdentity();
                meshes.add(new ModelMesh(mesh, modelMatrix));
            }

            Assimp.aiReleaseImport(aiScene);

            return new Model(meshes);
        } catch (AssimpImportFileFailedException e) {
            throw new ModelLoadFailedException(e);
        }
    }

    @Override
    public void unload(Model model) {
        for (ModelMesh modelMesh : model.getMeshes()) {
            Mesh mesh = modelMesh.getMesh();
            mVertexArrayCreator.destroy(mesh.getVertexArray());
            mIndexBufferCreator.destroy(mesh.getIndexBuffer());
        }
    }
}
