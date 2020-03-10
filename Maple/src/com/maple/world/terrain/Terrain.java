package com.maple.world.terrain;

import com.maple.utils.Flat2DArray;

public class Terrain {
    private Flat2DArray<Float> mHeightMap;

    public Terrain(Flat2DArray<Float> heightMap) {
        mHeightMap = heightMap;
    }

    public float get(int x, int z) {
        return mHeightMap.get(x, z);
    }

    public void set(int x, int z, float height) {
        mHeightMap.set(x, z, height);
    }

    public int getWidth() {
        return mHeightMap.getWidth();
    }

    public int getLength() {
        return mHeightMap.getHeight();
    }
}
