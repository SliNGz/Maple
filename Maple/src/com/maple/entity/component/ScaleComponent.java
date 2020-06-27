package com.maple.entity.component;

import com.maple.math.Vector3f;

public class ScaleComponent implements IComponent {
    private Vector3f mScale;

    public ScaleComponent(Vector3f scale) {
        mScale = scale;
    }

    public ScaleComponent() {
        this(new Vector3f(1, 1, 1));
    }

    public Vector3f get() {
        return mScale;
    }

    public void set(Vector3f scale) {
        mScale = scale;
    }
}
