package com.maple.world.terrain.heightmap;

public class DefaultHeightMapGeneratorBuilder {
    private int mMinHeight;
    private int mMaxHeight;
    private double mElevation;
    private double mRoughness;
    private double mDetail;
    private double mScale;

    public DefaultHeightMapGeneratorBuilder() {
        mMinHeight = 32;
        mMaxHeight = 96;
        mElevation = 0.75;
        mRoughness = 1.75;
        mDetail = 3.25;
        mScale = 256.0;
    }

    public DefaultHeightMapGeneratorBuilder setMinHeight(int minHeight) {
        mMinHeight = minHeight;
        return this;
    }

    public DefaultHeightMapGeneratorBuilder setMaxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
        return this;
    }

    public DefaultHeightMapGeneratorBuilder setElevation(double elevation) {
        mElevation = elevation;
        return this;
    }

    public DefaultHeightMapGeneratorBuilder setRoughness(double roughness) {
        mRoughness = roughness;
        return this;
    }

    public DefaultHeightMapGeneratorBuilder setDetail(double detail) {
        mDetail = detail;
        return this;
    }

    public DefaultHeightMapGeneratorBuilder setScale(double scale) {
        mScale = scale;
        return this;
    }

    public DefaultHeightMapGenerator build() {
        return new DefaultHeightMapGenerator(mMinHeight, mMaxHeight, mElevation, mRoughness, mDetail, mScale);
    }
}
