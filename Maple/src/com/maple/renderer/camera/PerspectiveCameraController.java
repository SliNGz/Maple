package com.maple.renderer.camera;

import com.maple.input.mouse.IMousePositionCallback;
import com.maple.math.Vector3f;

public class PerspectiveCameraController implements IMousePositionCallback {
    private static final float SENSITIVITY_MULTIPLIER = 0.0005F;

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
