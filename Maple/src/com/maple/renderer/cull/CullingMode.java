package com.maple.renderer.cull;

import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_CW;

public class CullingMode {
    public static final CullingMode CLOCKWISE = new CullingMode(GL_CW);
    public static final CullingMode COUNTER_CLOCKWISE = new CullingMode(GL_CCW);

    private int mValue;

    private CullingMode(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
