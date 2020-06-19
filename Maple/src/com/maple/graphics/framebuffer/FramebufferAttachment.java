package com.maple.graphics.framebuffer;

import static org.lwjgl.opengl.GL30.*;

public class FramebufferAttachment {
    public static final FramebufferAttachment[] COLOR_ATTACHMENTS = new FramebufferAttachment[32];
    public static final FramebufferAttachment DEPTH_ATTACHMENT = new FramebufferAttachment(GL_DEPTH_ATTACHMENT);
    public static final FramebufferAttachment STENCIL_ATTACHMENT = new FramebufferAttachment(GL_STENCIL_ATTACHMENT);
    public static final FramebufferAttachment DEPTH_STENCIL_ATTACHMENT = new FramebufferAttachment(GL_DEPTH_STENCIL_ATTACHMENT);

    static {
        for (int i = 0; i < COLOR_ATTACHMENTS.length; ++i) {
            COLOR_ATTACHMENTS[i] = new FramebufferAttachment(GL_COLOR_ATTACHMENT0 + i);
        }
    }

    private int mValue;

    private FramebufferAttachment(int value) {
        mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
