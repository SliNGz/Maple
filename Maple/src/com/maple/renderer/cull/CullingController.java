package com.maple.renderer.cull;

import static org.lwjgl.opengl.GL11.*;

public class CullingController {
    public void enable() {
        glEnable(GL_CULL_FACE);
    }

    public boolean isEnabled() {
        return glIsEnabled(GL_CULL_FACE);
    }

    public void setFaceCulling(CullingFace cullingFace) {
        glCullFace(cullingFace.getValue());
    }

    public void setMode(CullingMode cullingMode) {
        glFrontFace(cullingMode.getValue());
    }

    public void disable() {
        glDisable(GL_CULL_FACE);
    }
}