package com.maple.graphics.framebuffer;

import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class FramebufferBinder {
    public void bind(Framebuffer framebuffer) {
        glBindFramebuffer(GL_FRAMEBUFFER, framebuffer.getHandle());
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
