package com.maple.entity.component;

import com.maple.math.Vector3f;

public class PositionComponent implements IComponent {
    private Vector3f mPosition;

    public PositionComponent(Vector3f position) {
        mPosition = position;
    }

    public PositionComponent() {
        this(new Vector3f());
    }

    public Vector3f get() {
        return mPosition;
    }

    public void set(Vector3f position) {
        mPosition = position;
    }

    public void add(Vector3f position) {
        set(Vector3f.add(mPosition, position));
    }
}
