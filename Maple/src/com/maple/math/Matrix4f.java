package com.maple.math;

import com.maple.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Objects;

public class Matrix4f {
    private static final int ROWS_AMOUNT = 4;
    private static final int COLUMNS_AMOUNT = 4;

    private FloatBuffer mElements;

    public Matrix4f() {
        mElements = BufferUtils.createDirectFloatBuffer(COLUMNS_AMOUNT * ROWS_AMOUNT);
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
        Vector4f otherRow0 = other.getRow(0);
        Vector4f otherRow1 = other.getRow(1);
        Vector4f otherRow2 = other.getRow(2);
        Vector4f otherRow3 = other.getRow(3);

        for (int i = 0; i < COLUMNS_AMOUNT; ++i) {
            Vector4f column = getColumn(i);

            set(i, 0, Vector4f.dot(column, otherRow0));
            set(i, 1, Vector4f.dot(column, otherRow1));
            set(i, 2, Vector4f.dot(column, otherRow2));
            set(i, 3, Vector4f.dot(column, otherRow3));
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
}
