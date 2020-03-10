package com.maple.renderer.cull;

import static org.lwjgl.opengl.GL11.*;

public class CullingFace {
    public static final CullingFace FRONT = new CullingFace(GL_FRONT);
    public static final CullingFace BACK = new CullingFace(GL_BACK);
    public static final CullingFace FRONT_AND_BACK = new CullingFace(GL_FRONT_AND_BACK);

    private int mValue;

    private CullingFace(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
