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

    public Vector3f(Vector2f vector2f, float z) {
        this(vector2f.X, vector2f.Y, z);
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

    public static float distance(Vector3f left, Vector3f right) {
        return (float) Math.sqrt(Math.pow(left.X - right.X, 2) +
                                 Math.pow(left.Y - right.Y, 2) +
                                 Math.pow(left.Z - right.Z, 2));
    }

    public static float dot(Vector3f left, Vector3f right) {
        return left.X * right.X + left.Y * right.Y + left.Z * right.Z;
    }

    public static Vector3f cross(Vector3f left, Vector3f right) {
        return new Vector3f(left.Y * right.Z - left.Z * right.Y,
                            left.Z * right.X - left.X * right.Z,
                            left.X * right.Y - left.Y * right.X);
    }

    public static Vector3f lerp(Vector3f startValue, Vector3f endValue, float amount) {
        return new Vector3f(MathHelper.lerp(startValue.X, endValue.X, amount),
                            MathHelper.lerp(startValue.Y, endValue.Y, amount),
                            MathHelper.lerp(startValue.Z, endValue.Z, amount));
    }

    public static Vector3f createLookAt(float pitch, float yaw) {
        return new Vector3f((float) (Math.cos(pitch) * -Math.sin(yaw)),
                            (float) Math.sin(pitch),
                            (float) (Math.cos(pitch) * -Math.cos(yaw)));
    }
}
