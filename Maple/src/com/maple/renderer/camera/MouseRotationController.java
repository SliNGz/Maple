package com.maple.renderer.camera;

import com.maple.entity.component.RotationComponent;
import com.maple.input.mouse.IMousePositionCallback;
import com.maple.math.Vector3f;

public class MouseRotationController implements IMousePositionCallback {
    private static final float SENSITIVITY_MULTIPLIER = 0.0005F;

    private RotationComponent mRotationComponent;
    private float mSensitivity;

    public MouseRotationController(RotationComponent rotationComponent, float sensitivity) {
        mRotationComponent = rotationComponent;
        mSensitivity = sensitivity;
    }

    @Override
    public void invoke(float x, float y) {
        float pitch = -y;
        float yaw = x;

        mRotationComponent.add(new Vector3f(pitch, yaw, 0).multiply(mSensitivity * SENSITIVITY_MULTIPLIER));
    }
}
