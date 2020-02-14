package com.maple.math;

import java.util.Objects;

public class Vector4f {
    public float X, Y, Z, W;

    public Vector4f(float x, float y, float z, float w) {
        X = x;
        Y = y;
        Z = z;
        W = w;
    }

    public Vector4f() {
        this(0, 0, 0, 0);
    }

    public Vector4f(Vector4f other) {
        this(other.X, other.Y, other.Z, other.W);
    }

    public Vector4f add(Vector4f other) {
        X += other.X;
        Y += other.Y;
        Z += other.Z;
        W += other.W;
        return this;
    }

    public Vector4f subtract(Vector4f other) {
        X -= other.X;
        Y -= other.Y;
        Z -= other.Z;
        W -= other.W;
        return this;
    }

    public Vector4f multiply(Vector4f other) {
        X *= other.X;
        Y *= other.Y;
        Z *= other.Z;
        W *= other.W;
        return this;
    }

    public Vector4f multiply(float scalar) {
        X *= scalar;
        Y *= scalar;
        Z *= scalar;
        W *= scalar;
        return this;
    }

    public Vector4f divide(Vector4f other) {
        X /= other.X;
        Y /= other.Y;
        Z /= other.Z;
        W /= other.W;
        return this;
    }

    public Vector4f divide(float scalar) {
        X /= scalar;
        Y /= scalar;
        Z /= scalar;
        W /= scalar;
        return this;
    }

    public Vector4f negate() {
        return multiply(-1);
    }

    public float getLength() {
        return (float) Math.sqrt(X * X + Y * Y + Z * Z + W * W);
    }

    public Vector4f normalize() {
        return divide(getLength());
    }

    @Override
    public String toString() {
        return String.format("{X:%.3f Y:%.3f Z:%.3f W:%.3f}", X, Y, Z, W);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector4f other = (Vector4f) o;
        return Float.compare(X, other.X) == 0 &&
               Float.compare(Y, other.Y) == 0 &&
               Float.compare(Z, other.Z) == 0 &&
               Float.compare(W, other.W) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y, Z, W);
    }

    public static Vector4f add(Vector4f left, Vector4f right) {
        return new Vector4f(left).add(right);
    }

    public static Vector4f subtract(Vector4f left, Vector4f right) {
        return new Vector4f(left).subtract(right);
    }

    public static Vector4f multiply(Vector4f left, Vector4f right) {
        return new Vector4f(left).multiply(right);
    }

    public static Vector4f multiply(Vector4f left, float scalar) {
        return new Vector4f(left).multiply(scalar);
    }

    public static Vector4f divide(Vector4f left, Vector4f right) {
        return new Vector4f(left).divide(right);
    }

    public static Vector4f divide(Vector4f left, float scalar) {
        return new Vector4f(left).divide(scalar);
    }

    public static Vector4f negate(Vector4f vector) {
        return new Vector4f(vector).negate();
    }

    public static float getLength(Vector4f vector) {
        return vector.getLength();
    }

    public static Vector4f normalize(Vector4f vector) {
        return new Vector4f(vector).normalize();
    }

    public static float dot(Vector4f left, Vector4f right) {
        return left.X * right.X + left.Y * right.Y + left.Z * right.Z + left.W * right.W;
    }
}
