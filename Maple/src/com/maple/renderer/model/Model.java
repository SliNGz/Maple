package com.maple.renderer.model;

import java.util.List;

public class Model {
    private List<ModelMesh> mMeshes;

    public Model(List<ModelMesh> meshes) {
        mMeshes = meshes;
    }

    public List<ModelMesh> getMeshes() {
        return mMeshes;
    }
}
