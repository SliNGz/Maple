package com.maple.renderer.camera;

import com.maple.math.Matrix4f;
import com.maple.math.Vector3f;

public class OrthographicCamera implements ICamera {
    private Vector3f mPosition;
    private Vector3f mRotation;

    private float mLeft;
    private float mRight;
    private float mBottom;
    private float mTop;
    private float mNear;
    private float mFar;
    private float mScale;

    private Matrix4f mView;
    private Matrix4f mProjection;
    private Matrix4f mViewProjection;

    public OrthographicCamera(int width, int height, float near, float far) {
        this(0, width, 0, height, near, far);
    }

    public OrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
        setViewFields(new Vector3f(), new Vector3f());
        setProjectionFields(left, right, bottom, top, near, far, 1.0F);
        updateViewProjectionMatrix();
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3f position) {
        setViewFields(position, mRotation);
        updateViewProjectionMatrix();
    }

    public Vector3f getRotation() {
        return mRotation;
    }

    public void setRotation(Vector3f rotation) {
        setViewFields(mPosition, rotation);
        updateViewProjectionMatrix();
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        setProjectionFields(mLeft, mRight, mBottom, mTop, mNear, mFar, scale);
        updateViewProjectionMatrix();
    }

    private void setViewFields(Vector3f position, Vector3f rotation) {
        mPosition = position;
        mRotation = rotation;
        mView = Matrix4f.multiply(Matrix4f.createTranslation(Vector3f.negate(mPosition)),
                                  Matrix4f.createRotationX(mRotation.X),
                                  Matrix4f.createRotationY(mRotation.Y),
                                  Matrix4f.createRotationZ(mRotation.Z));
    }

    private void setProjectionFields(float left, float right, float bottom, float top, float near, float far, float scale) {
        mLeft = left;
        mRight = right;
        mBottom = bottom;
        mTop = top;
        mNear = near;
        mFar = far;
        mScale = scale;
        mProjection = Matrix4f.createOrthographic(mLeft * mScale,
                                                  mRight * mScale,
                                                  mBottom * mScale,
                                                  mTop * mScale, mNear, mFar);
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return mViewProjection;
    }

    private void updateViewProjectionMatrix() {
        mViewProjection = Matrix4f.multiply(mProjection, mView);
    }
}
