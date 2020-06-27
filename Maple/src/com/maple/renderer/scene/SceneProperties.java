package com.maple.renderer.scene;

import com.maple.renderer.camera.ICamera;
import com.maple.renderer.scene.exceptions.CameraNotSetException;
import com.maple.utils.Color;

public class SceneProperties {
    private ICamera mCamera;
    private Color mSkyColor;

    public SceneProperties() {
        mCamera = null;
        mSkyColor = new Color(0.392F, 0.584F, 0.929F);
    }

    public ICamera getCamera() {
        if (mCamera == null) {
            throw new CameraNotSetException();
        }

        return mCamera;
    }

    public void setCamera(ICamera camera) {
        mCamera = camera;
    }

    public Color getSkyColor() {
        return mSkyColor;
    }

    public void setSkyColor(Color skyColor) {
        mSkyColor = skyColor;
    }
}
