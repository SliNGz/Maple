package com.maple.world.terrain.heightmap;

import com.maple.utils.SimplexNoise;

public class DefaultHeightMapGenerator implements IHeightMapGenerator {
    private int mMinHeight;
    private int mMaxHeight;
    private double mElevation;
    private double mRoughness;
    private double mDetail;
    private double mScale;

    public DefaultHeightMapGenerator(int minHeight, int maxHeight,
                                     double elevation, double roughness, double detail,
                                     double scale) {
        mMinHeight = minHeight;
        mMaxHeight = maxHeight;
        mElevation = elevation;
        mRoughness = roughness;
        mDetail = detail;
        mScale = scale;
    }

    @Override
    public double generateHeightAt(int x, int z) {
        double scaledX = x / mScale;
        double scaledZ = z / mScale;

        double elevationNoise = SimplexNoise.noise(scaledX * mElevation, scaledZ * mElevation);
        double roughnessNoise = SimplexNoise.noise(scaledX * mRoughness, scaledZ * mRoughness);
        double detailNoise = SimplexNoise.noise(scaledX * mDetail, scaledZ * mDetail);

        double combinedNoise = 0.6 * elevationNoise + 0.3 * roughnessNoise + 0.1 * detailNoise;
        double normalizedNoise = (combinedNoise + 1) / 2.0;

        return mMinHeight + normalizedNoise * (mMaxHeight - mMinHeight);
    }
}
