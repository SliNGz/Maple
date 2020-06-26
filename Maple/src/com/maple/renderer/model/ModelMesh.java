package com.maple.renderer.model;

import com.maple.math.Matrix4f;
import com.maple.renderer.mesh.Mesh;

public class ModelMesh {
    private Mesh mMesh;
    private Matrix4f mModelMatrix;

    public ModelMesh(Mesh mesh, Matrix4f modelMatrix) {
        mMesh = mesh;
        mModelMatrix = modelMatrix;
    }

    public Mesh getMesh() {
        return mMesh;
    }

    public Matrix4f getModelMatrix() {
        return mModelMatrix;
    }

    public void setModelMatrix(Matrix4f modelMatrix) {
        mModelMatrix = modelMatrix;
    }
}
