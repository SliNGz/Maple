package com.maple.renderer.camera;

import com.maple.math.Matrix4f;
import com.maple.math.Vector3f;

public class OrthographicCamera implements ICamera {
    private Vector3f mPosition;
    private float mRoll;

    private float mLeft;
    private float mRight;
    private float mBottom;
    private float mTop;
    private float mScale;

    private Matrix4f mView;
    private Matrix4f mProjection;
    private Matrix4f mViewProjection;

    public OrthographicCamera(int width, int height) {
        this(0, width, 0, height);
    }

    public OrthographicCamera(float left, float right, float bottom, float top) {
        setViewFields(new Vector3f(), 0.0F);
        setProjectionFields(left, right, bottom, top, 1.0F);
        updateViewProjectionMatrix();
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public void setPosition(Vector3f position) {
        setViewFields(position, mRoll);
        updateViewProjectionMatrix();
    }

    public float getRoll() {
        return mRoll;
    }

    public void setRoll(float roll) {
        setViewFields(mPosition, roll);
        updateViewProjectionMatrix();
    }

    public float getScale() {
        return mScale;
    }

    public void setScale(float scale) {
        setProjectionFields(mLeft, mRight, mBottom, mTop, scale);
        updateViewProjectionMatrix();
    }

    private void setViewFields(Vector3f position, float roll) {
        mPosition = position;
        mRoll = roll;
        mView = Matrix4f.multiply(Matrix4f.createTranslation(Vector3f.negate(mPosition)), Matrix4f.createRotationZ(mRoll));
    }

    private void setProjectionFields(float left, float right, float bottom, float top, float scale) {
        mLeft = left;
        mRight = right;
        mBottom = bottom;
        mTop = top;
        mScale = scale;
        mProjection = Matrix4f.createOrthographic(mLeft * mScale,
                                                  mRight * mScale,
                                                  mBottom * mScale,
                                                  mTop * mScale, -1.0F, 1.0F);
    }

    @Override
    public Matrix4f getViewProjectionMatrix() {
        return mViewProjection;
    }

    private void updateViewProjectionMatrix() {
        mViewProjection = Matrix4f.multiply(mProjection, mView);
    }
}
