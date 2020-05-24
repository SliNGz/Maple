package com.maple.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Objects;

public class Matrix4f {
    private static final int ROWS_AMOUNT = 4;
    private static final int COLUMNS_AMOUNT = 4;

    private FloatBuffer mElements;

    public Matrix4f() {
        mElements = BufferUtils.createFloatBuffer(COLUMNS_AMOUNT * ROWS_AMOUNT);
        mElements.rewind();
    }

    public Matrix4f(float m00, float m10, float m20, float m30,
                    float m01, float m11, float m21, float m31,
                    float m02, float m12, float m22, float m32,
                    float m03, float m13, float m23, float m33) {
        this();
        mElements.put(new float[]{m00, m01, m02, m03,
                                  m10, m11, m12, m13,
                                  m20, m21, m22, m23,
                                  m30, m31, m32, m33});
        mElements.rewind();
    }

    public Matrix4f(Matrix4f other) {
        this();
        FloatBuffer otherElements = other.mElements;
        otherElements.rewind();
        mElements.put(otherElements);
        otherElements.rewind();
    }

    public FloatBuffer getElements() {
        return mElements;
    }

    private int getIndex(int x, int y) {
        return x * COLUMNS_AMOUNT + y;
    }

    public float get(int index) {
        return mElements.get(index);
    }

    public float get(int x, int y) {
        return get(getIndex(x, y));
    }

    public void set(int index, float value) {
        mElements.put(index, value);
    }

    public void set(int x, int y, float value) {
        set(getIndex(x, y), value);
    }

    private Vector4f getColumn(int index) {
        return new Vector4f(get(index, 0), get(index, 1), get(index, 2), get(index, 3));
    }

    private Vector4f getRow(int index) {
        return new Vector4f(get(0, index), get(1, index), get(2, index), get(3, index));
    }

    public Matrix4f multiply(Matrix4f other) {
        Vector4f row0 = getRow(0);
        Vector4f row1 = getRow(1);
        Vector4f row2 = getRow(2);
        Vector4f row3 = getRow(3);

        for (int i = 0; i < COLUMNS_AMOUNT; ++i) {
            Vector4f otherColumn = other.getColumn(i);

            set(i, 0, Vector4f.dot(row0, otherColumn));
            set(i, 1, Vector4f.dot(row1, otherColumn));
            set(i, 2, Vector4f.dot(row2, otherColumn));
            set(i, 3, Vector4f.dot(row3, otherColumn));
        }

        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ROWS_AMOUNT; ++i) {
            stringBuilder.append(String.format("[%.3f %.3f %.3f %.3f]", get(0, i), get(1, i), get(2, i), get(3, i)));

            if (i < ROWS_AMOUNT - 1) {
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix4f other = (Matrix4f) o;
        FloatBuffer otherElements = other.mElements;
        otherElements.rewind();
        mElements.rewind();

        return mElements.equals(otherElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mElements);
    }

    public static Matrix4f multiply(Matrix4f left, Matrix4f right) {
        return new Matrix4f(left).multiply(right);
    }

    public static Matrix4f createIdentity() {
        return new Matrix4f(1, 0, 0, 0,
                            0, 1, 0, 0,
                            0, 0, 1, 0,
                            0, 0, 0, 1);
    }

    public static Matrix4f createLookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f zAxis = Vector3f.normalize(Vector3f.subtract(target, eye));
        Vector3f xAxis = Vector3f.normalize(Vector3f.cross(up, zAxis));
        Vector3f yAxis = Vector3f.cross(zAxis, xAxis);

        float m00 = xAxis.X;
        float m10 = xAxis.Y;
        float m20 = xAxis.Z;
        float m30 = -Vector3f.dot(xAxis, eye);

        float m01 = yAxis.X;
        float m11 = yAxis.Y;
        float m21 = yAxis.Z;
        float m31 = -Vector3f.dot(yAxis, eye);

        float m02 = -zAxis.X;
        float m12 = -zAxis.Y;
        float m22 = -zAxis.Z;
        float m32 = Vector3f.dot(zAxis, eye);

        return new Matrix4f(m00, m10, m20, m30,
                            m01, m11, m21, m31,
                            m02, m12, m22, m32,
                            0, 0, 0, 1);
    }

    public static Matrix4f createPerspective(float fov, float aspectRatio, float nearPlane, float farPlane) {
        float constant = 1.0F / (float) Math.tan(MathHelper.toRadians(fov * 0.5F));
        float planesSum = farPlane + nearPlane;
        float negatedPlanesDiff = nearPlane - farPlane;

        float m00 = constant / aspectRatio;
        float m11 = constant;
        float m22 = planesSum / negatedPlanesDiff;
        float m32 = (2.0F * farPlane * nearPlane) / negatedPlanesDiff;
        float m23 = -1.0F;

        return new Matrix4f(m00, 0, 0, 0,
                            0, m11, 0, 0,
                            0, 0, m22, m32,
                            0, 0, m23, 0);
    }

    public static Matrix4f createOrthographic(float left, float right, float bottom, float top, float nearPlane, float farPlane) {
        float m00 = 2.0F / (right - left);
        float m11 = 2.0F / (top - bottom);
        float m22 = -2.0F / (farPlane - nearPlane);
        float m30 = (right + left) / -(right - left);
        float m31 = (top + bottom) / -(top - bottom);
        float m32 = (farPlane + nearPlane) / -(farPlane - nearPlane);
        float m33 = 1.0F;

        return new Matrix4f(m00, 0, 0, m30,
                            0, m11, 0, m31,
                            0, 0, m22, m32,
                            0, 0, 0, m33);
    }


    public static Matrix4f createTranslation(Vector3f position) {
        float m30 = position.X;
        float m31 = position.Y;
        float m32 = position.Z;

        return new Matrix4f(1, 0, 0, m30,
                            0, 1, 0, m31,
                            0, 0, 1, m32,
                            0, 0, 0, 1);
    }

    public static Matrix4f createScale(Vector3f scale) {
        float m00 = scale.X;
        float m11 = scale.Y;
        float m22 = scale.Z;

        return new Matrix4f(m00, 0, 0, 0,
                            0, m11, 0, 0,
                            0, 0, m22, 0,
                            0, 0, 0, 1);
    }

    public static Matrix4f createScale(float scale) {
        return createScale(new Vector3f(scale, scale, scale));
    }

    public static Matrix4f createRotationX(float radians) {
        float cosTheta = (float) Math.cos(radians);
        float sinTheta = (float) Math.sin(radians);

        float m11 = cosTheta;
        float m12 = sinTheta;
        float m21 = -sinTheta;
        float m22 = cosTheta;

        return new Matrix4f(1, 0, 0, 0,
                            0, m11, m21, 0,
                            0, m12, m22, 0,
                            0, 0, 0, 1);
    }

    public static Matrix4f createRotationY(float radians) {
        float cosTheta = (float) Math.cos(radians);
        float sinTheta = (float) Math.sin(radians);

        float m00 = cosTheta;
        float m02 = -sinTheta;
        float m20 = sinTheta;
        float m22 = cosTheta;

        return new Matrix4f(m00, 0, m20, 0,
                            0, 1, 0, 0,
                            m02, 0, m22, 0,
                            0, 0, 0, 1);
    }

    public static Matrix4f createRotationZ(float radians) {
        float cosTheta = (float) Math.cos(radians);
        float sinTheta = (float) Math.sin(radians);

        float m00 = cosTheta;
        float m01 = sinTheta;
        float m10 = -sinTheta;
        float m11 = cosTheta;

        return new Matrix4f(m00, m10, 0, 0,
                            m01, m11, 0, 0,
                            0, 0, 1, 0,
                            0, 0, 0, 1);
    }
}
