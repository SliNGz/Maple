package com.maple.world.terrain;

import com.maple.utils.Flat2DArray;
import com.maple.world.terrain.heightmap.IHeightMapGenerator;

public class TerrainCreator {
    private IHeightMapGenerator mHeightMapGenerator;

    public TerrainCreator(IHeightMapGenerator heightMapGenerator) {
        mHeightMapGenerator = heightMapGenerator;
    }

    public Terrain create(int width, int length) {
        width += 1;
        length += 1;

        Flat2DArray<Float> heightMap = new Flat2DArray<>(width, length);

        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < length; ++z) {
                heightMap.set(x, z, (float) mHeightMapGenerator.generateHeightAt(x, z));
            }
        }

        return new Terrain(heightMap);
    }
}
