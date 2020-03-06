package com.maple.renderer.camera;

import com.maple.math.Matrix4f;

public class CameraStub implements ICamera {
    private Matrix4f mIdentity;

    public CameraStub() {
        mIdentity = Matrix4f.createIdentity();
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return mIdentity;
    }
}
