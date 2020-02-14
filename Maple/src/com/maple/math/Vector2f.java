package com.maple.math;

import java.util.Objects;

public class Vector2f {
    public float X, Y;

    public Vector2f(float x, float y) {
        X = x;
        Y = y;
    }

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f(Vector2f other) {
        this(other.X, other.Y);
    }

    public Vector2f add(Vector2f other) {
        X += other.X;
        Y += other.Y;
        return this;
    }

    public Vector2f subtract(Vector2f other) {
        X -= other.X;
        Y -= other.Y;
        return this;
    }

    public Vector2f multiply(Vector2f other) {
        X *= other.X;
        Y *= other.Y;
        return this;
    }

    public Vector2f multiply(float scalar) {
        X *= scalar;
        Y *= scalar;
        return this;
    }

    public Vector2f divide(Vector2f other) {
        X /= other.X;
        Y /= other.Y;
        return this;
    }

    public Vector2f divide(float scalar) {
        X /= scalar;
        Y /= scalar;
        return this;
    }

    public Vector2f negate() {
        return multiply(-1);
    }

    public float getLength() {
        return (float) Math.sqrt(X * X + Y * Y);
    }

    public Vector2f normalize() {
        return divide(getLength());
    }

    @Override
    public String toString() {
        return String.format("{X:%.3f Y:%.3f}", X, Y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f other = (Vector2f) o;
        return Float.compare(X, other.X) == 0 &&
               Float.compare(Y, other.Y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    public static Vector2f add(Vector2f left, Vector2f right) {
        return new Vector2f(left).add(right);
    }

    public static Vector2f subtract(Vector2f left, Vector2f right) {
        return new Vector2f(left).subtract(right);
    }

    public static Vector2f multiply(Vector2f left, Vector2f right) {
        return new Vector2f(left).multiply(right);
    }

    public static Vector2f multiply(Vector2f left, float scalar) {
        return new Vector2f(left).multiply(scalar);
    }

    public static Vector2f divide(Vector2f left, Vector2f right) {
        return new Vector2f(left).divide(right);
    }

    public static Vector2f divide(Vector2f left, float scalar) {
        return new Vector2f(left).divide(scalar);
    }

    public static Vector2f negate(Vector2f vector) {
        return new Vector2f(vector).negate();
    }

    public static float getLength(Vector2f vector) {
        return vector.getLength();
    }

    public static Vector2f normalize(Vector2f vector) {
        return new Vector2f(vector).normalize();
    }

    public static float dot(Vector2f left, Vector2f right) {
        return left.X * right.X + left.Y * right.Y;
    }
}
