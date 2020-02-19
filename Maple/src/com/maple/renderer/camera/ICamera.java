package com.maple.renderer.camera;

import com.maple.math.Matrix4f;

public interface ICamera {
    Matrix4f getViewProjectionMatrix();
}
