package com.maple.renderer.mesh.terrain;

import com.maple.utils.Color;

public interface ITerrainColorizer {
    Color getColorAt(int x, float y, int z);
}
