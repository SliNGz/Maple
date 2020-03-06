package com.maple.utils;

import com.maple.math.MathHelper;

public class Color {
    private int mR;
    private int mG;
    private int mB;
    private int mA;

    public Color(int r, int g, int b, int a) {
        setR(r);
        setG(g);
        setB(b);
        setA(a);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public Color(float r, float g, float b, float a) {
        this((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1.0F);
    }

    public int getR() {
        return mR;
    }

    public Color setR(int r) {
        mR = MathHelper.clamp(r, 0, 255);
        return this;
    }

    public int getG() {
        return mG;
    }

    public Color setG(int g) {
        mG = MathHelper.clamp(g, 0, 255);
        return this;
    }

    public int getB() {
        return mB;
    }

    public Color setB(int b) {
        mB = MathHelper.clamp(b, 0, 255);
        return this;
    }

    public int getA() {
        return mA;
    }

    public Color setA(int a) {
        mA = MathHelper.clamp(a, 0, 255);
        return this;
    }
}
