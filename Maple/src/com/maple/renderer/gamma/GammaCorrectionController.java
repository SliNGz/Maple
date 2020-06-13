package com.maple.renderer.gamma;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;

public class GammaCorrectionController {
    public void enable() {
        glEnable(GL_FRAMEBUFFER_SRGB);
    }

    public boolean isEnabled() {
        return glIsEnabled(GL_FRAMEBUFFER_SRGB);
    }

    public void disable() {
        glDisable(GL_FRAMEBUFFER_SRGB);
    }
}
