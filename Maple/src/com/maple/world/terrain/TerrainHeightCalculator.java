package com.maple.world.terrain;

import com.maple.math.MathHelper;
import com.maple.math.Vector3f;

public class TerrainHeightCalculator {
    private static Vector3f getVertexPosition(Terrain terrain, int x, int z) {
        return new Vector3f(x, terrain.get(x, z), z);
    }

    public static float calculate(Terrain terrain, float x, float z) {
        int width = terrain.getWidth() - 1;
        int length = terrain.getLength() - 1;

        x = MathHelper.clamp(x, 0, width);
        z = MathHelper.clamp(z, 0, width);

        int vertexX = (int) MathHelper.clamp(x, 0, width - 1);
        int vertexZ = (int) MathHelper.clamp(z, 0, length - 1);

        Vector3f p1 = getVertexPosition(terrain, vertexX, vertexZ + 1);
        Vector3f p2 = getVertexPosition(terrain, vertexX + 1, vertexZ);

        Vector3f p3;
        if (x < 1 - z) {
            p3 = getVertexPosition(terrain, vertexX, vertexZ);
        } else {
            p3 = getVertexPosition(terrain, vertexX + 1, vertexZ + 1);
        }

        return MathHelper.barryCentric(p1, p2, p3, x, z);
    }
}
