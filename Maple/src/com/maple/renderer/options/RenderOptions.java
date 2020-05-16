package com.maple.renderer.options;

import com.maple.graphics.shader.effect.Effect;
import com.maple.math.Matrix4f;
import com.maple.renderer.camera.CameraStub;
import com.maple.renderer.camera.ICamera;

public class RenderOptions {
    private ICamera mCamera;
    private Matrix4f mTransform;
    private Effect mEffect;

    public RenderOptions() {
        mCamera = new CameraStub();
        mTransform = Matrix4f.createIdentity();
        mEffect = new Effect();
    }

    public ICamera getCamera() {
        return mCamera;
    }

    public void setCamera(ICamera camera) {
        mCamera = camera;
    }

    public Matrix4f getTransform() {
        return mTransform;
    }

    public void setTransform(Matrix4f transform) {
        mTransform = transform;
    }

    public Effect getEffect() {
        return mEffect;
    }

    public void setEffect(Effect effect) {
        mEffect = effect;
    }
}
