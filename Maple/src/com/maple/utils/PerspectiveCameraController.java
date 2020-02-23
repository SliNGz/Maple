package com.maple.utils;

import com.maple.input.mouse.IMousePositionCallback;
import com.maple.math.Vector3f;
import com.maple.renderer.camera.PerspectiveCamera;

public class PerspectiveCameraController implements IMousePositionCallback {
    private static final float SENSITIVITY_MULTIPLIER = 0.01F;

    private PerspectiveCamera mCamera;
    private float mSensitivity;

    public PerspectiveCameraController(PerspectiveCamera camera, float sensitivity) {
        mCamera = camera;
        mSensitivity = sensitivity;
    }

    @Override
    public void invoke(float x, float y) {
        float pitch = -y;
        float yaw = x;

        mCamera.addRotation(new Vector3f(pitch, yaw, 0).multiply(mSensitivity * SENSITIVITY_MULTIPLIER));
    }
}
