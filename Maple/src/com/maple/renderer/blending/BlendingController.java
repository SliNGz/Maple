package com.maple.renderer.blending;

import static org.lwjgl.opengl.GL11.*;

public class BlendingController {
    public void enable() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public boolean isEnabled() {
        return glIsEnabled(GL_BLEND);
    }

    public void disable() {
        glDisable(GL_BLEND);
    }
}
