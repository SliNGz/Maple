package com.maple.math;

public class MathHelper {
    public static float PI = (float) Math.PI;

    public static float toRadians(float degrees) {
        return degrees * (PI / 180.0F);
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }

        return value;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }

        return value;
    }

    public static float wrapAngle(float angle) {
        if (angle <= 0)
            angle += Math.PI * 2;
        else if (angle > Math.PI)
            angle -= Math.PI * 2;

        return angle;
    }

    public static float clampPitch(float pitch) {
        return clamp(pitch, (float) -Math.PI / 2.0F + 0.0001F, (float) Math.PI / 2.0F - 0.0001F);
    }

    public static float lerp(float startValue, float endValue, float amount) {
        return startValue + (endValue - startValue) * amount;
    }

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, float x, float z) {
        float det = (p2.Z - p3.Z) * (p1.X - p3.X) + (p3.X - p2.X) * (p1.Z - p3.Z);
        float l1 = ((p2.Z - p3.Z) * (x - p3.X) + (p3.X - p2.X) * (z - p3.Z)) / det;
        float l2 = ((p3.Z - p1.Z) * (x - p3.X) + (p1.X - p3.X) * (z - p3.Z)) / det;
        float l3 = 1.0f - l1 - l2;

        return l1 * p1.Y + l2 * p2.Y + l3 * p3.Y;
    }
}
