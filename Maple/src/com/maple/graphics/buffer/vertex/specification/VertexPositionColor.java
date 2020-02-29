package com.maple.graphics.buffer.vertex.specification;

import com.maple.math.Vector3f;
import com.maple.utils.Color;

public class VertexPositionColor {
    private Vector3f mPosition;
    private Color mColor;

    public VertexPositionColor(Vector3f position, Color color) {
        mPosition = position;
        mColor = color;
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public Color getColor() {
        return mColor;
    }
}
