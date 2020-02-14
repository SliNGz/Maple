package com.maple.math;

import java.util.Objects;

public class Vector3f {
    public float X, Y, Z;

    public Vector3f(float x, float y, float z) {
        X = x;
        Y = y;
        Z = z;
    }

    public Vector3f() {
        this(0, 0, 0);
    }

    public Vector3f(Vector3f other) {
        this(other.X, other.Y, other.Z);
    }

    public Vector3f add(Vector3f other) {
        X += other.X;
        Y += other.Y;
        Z += other.Z;
        return this;
    }

    public Vector3f subtract(Vector3f other) {
        X -= other.X;
        Y -= other.Y;
        Z -= other.Z;
        return this;
    }

    public Vector3f multiply(Vector3f other) {
        X *= other.X;
        Y *= other.Y;
        Z *= other.Z;
        return this;
    }

    public Vector3f multiply(float scalar) {
        X *= scalar;
        Y *= scalar;
        Z *= scalar;
        return this;
    }

    public Vector3f divide(Vector3f other) {
        X /= other.X;
        Y /= other.Y;
        Z /= other.Z;
        return this;
    }

    public Vector3f divide(float scalar) {
        X /= scalar;
        Y /= scalar;
        Z /= scalar;
        return this;
    }

    public Vector3f negate() {
        return multiply(-1);
    }

    public float getLength() {
        return (float) Math.sqrt(X * X + Y * Y + Z * Z);
    }

    public Vector3f normalize() {
        return divide(getLength());
    }

    @Override
    public String toString() {
        return String.format("{X:%.3f Y:%.3f Z:%.3f}", X, Y, Z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f other = (Vector3f) o;
        return Float.compare(X, other.X) == 0 &&
               Float.compare(Y, other.Y) == 0 &&
               Float.compare(Z, other.Z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y, Z);
    }

    public static Vector3f add(Vector3f left, Vector3f right) {
        return new Vector3f(left).add(right);
    }

    public static Vector3f subtract(Vector3f left, Vector3f right) {
        return new Vector3f(left).subtract(right);
    }

    public static Vector3f multiply(Vector3f left, Vector3f right) {
        return new Vector3f(left).multiply(right);
    }

    public static Vector3f multiply(Vector3f left, float scalar) {
        return new Vector3f(left).multiply(scalar);
    }

    public static Vector3f divide(Vector3f left, Vector3f right) {
        return new Vector3f(left).divide(right);
    }

    public static Vector3f divide(Vector3f left, float scalar) {
        return new Vector3f(left).divide(scalar);
    }

    public static Vector3f negate(Vector3f vector) {
        return new Vector3f(vector).negate();
    }

    public static float getLength(Vector3f vector) {
        return vector.getLength();
    }

    public static Vector3f normalize(Vector3f vector) {
        return new Vector3f(vector).normalize();
    }

    public static float dot(Vector3f left, Vector3f right) {
        return left.X * right.X + left.Y * right.Y + left.Z * right.Z;
    }
}
