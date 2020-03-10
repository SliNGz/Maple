package com.maple.renderer;

import static org.lwjgl.opengl.GL11.*;

public class DepthTestController {
    public void enable() {
        glEnable(GL_DEPTH_TEST);
    }

    public boolean isEnabled() {
        return glIsEnabled(GL_DEPTH_TEST);
    }

    public void disable() {
        glDisable(GL_DEPTH_TEST);
    }
}
