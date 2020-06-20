package com.maple.entity.component;

import com.maple.math.Vector3f;

public class RotationComponent {
    private Vector3f mRotation;

    public RotationComponent(Vector3f rotation) {
        mRotation = rotation;
    }

    public RotationComponent() {
        this(new Vector3f());
    }

    public Vector3f get() {
        return mRotation;
    }

    public void set(Vector3f rotation) {
        mRotation = rotation;
    }

    public void add(Vector3f rotation) {
        set(Vector3f.add(mRotation, rotation));
    }
}
