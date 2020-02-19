package com.maple.renderer.camera;

import com.maple.math.MathHelper;
import com.maple.math.Matrix4f;
import com.maple.math.Vector3f;

public class PerspectiveCamera implements ICamera {
    private Vector3f mPosition;
    private Vector3f mRotation;
    private Vector3f mUp;

    private float mFov;
    private float mAspectRatio;
    private float mNearPlane;
    private float mFarPlane;

    private Matrix4f mView;
    private Matrix4f mProjection;
    private Matrix4f mViewProjection;

    public PerspectiveCamera(float fov, float aspectRatio, float nearPlane, float farPlane) {
        setViewFields(new Vector3f(), new Vector3f(), new Vector3f(0, 1, 0));
        setProjectionFields(fov, aspectRatio, nearPlane, farPlane);
        updateViewProjectionMatrix();
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3f position) {
        setViewFields(position, mRotation, mUp);
        updateViewProjectionMatrix();
    }

    public Vector3f getRotation() {
        return mRotation;
    }

    public void setRotation(Vector3f rotation) {
        setViewFields(mPosition, rotation, mUp);
        updateViewProjectionMatrix();
    }

    public Vector3f getUp() {
        return mUp;
    }

    public void setUp(Vector3f up) {
        setViewFields(mPosition, mRotation, up);
        updateViewProjectionMatrix();
    }

    public float getFov() {
        return mFov;
    }

    public void setFov(float fov) {
        setProjectionFields(fov, mAspectRatio, mNearPlane, mFarPlane);
        updateViewProjectionMatrix();
    }

    public float getAspectRatio() {
        return mAspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        setProjectionFields(mFov, aspectRatio, mNearPlane, mFarPlane);
        updateViewProjectionMatrix();
    }

    public float getNearPlane() {
        return mNearPlane;
    }

    public void setNearPlane(float nearPlane) {
        setProjectionFields(mFov, mAspectRatio, nearPlane, mFarPlane);
        updateViewProjectionMatrix();
    }

    public float getFarPlane() {
        return mFarPlane;
    }

    public void setFarPlane(float farPlane) {
        setProjectionFields(mFov, mAspectRatio, mNearPlane, farPlane);
        updateViewProjectionMatrix();
    }

    private void setViewFields(Vector3f position, Vector3f rotation, Vector3f up) {
        mPosition = position;
        mRotation = rotation;
        mRotation.X = MathHelper.clampPitch(rotation.X);
        mRotation.Y = MathHelper.wrapAngle(rotation.Y);
        mUp = up;
        Vector3f lookAt = Vector3f.createLookAt(mRotation.Y, mRotation.X);
        mView = Matrix4f.createLookAt(mPosition, Vector3f.add(mPosition, lookAt), mUp);
    }

    private void setProjectionFields(float fov, float aspectRatio, float nearPlane, float farPlane) {
        mFov = fov;
        mAspectRatio = aspectRatio;
        mNearPlane = nearPlane;
        mFarPlane = farPlane;
        mProjection = Matrix4f.createPerspective(mFov, mAspectRatio, mNearPlane, mFarPlane);
    }

    private void updateViewProjectionMatrix() {
        mViewProjection = Matrix4f.multiply(mView, mProjection);
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return mViewProjection;
    }
}
