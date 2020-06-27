package com.maple.renderer.model;

import com.maple.math.Matrix4f;
import com.maple.renderer.Renderer;
import com.maple.renderer.mesh.Mesh;

public class ModelRenderer {
    private Renderer mRenderer;

    public ModelRenderer(Renderer renderer) {
        mRenderer = renderer;
    }

    public void render(Model model, Matrix4f modelMatrix) {
        for (ModelMesh modelMesh : model.getMeshes()) {
            Mesh mesh = modelMesh.getMesh();
            Matrix4f meshModelMatrix = modelMesh.getModelMatrix();

            mRenderer.bindMesh(mesh);
            mRenderer.setModelMatrix(Matrix4f.multiply(meshModelMatrix, modelMatrix));
            mRenderer.render();
        }
    }

    public Renderer getRenderer() {
        return mRenderer;
    }
}
