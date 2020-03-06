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
}
