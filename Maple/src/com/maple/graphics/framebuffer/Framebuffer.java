package com.maple.graphics.framebuffer;

import com.maple.graphics.framebuffer.attachment.FramebufferAttachment;
import com.maple.graphics.texture.Texture2D;

public class Framebuffer {
    private int mHandle;
    private FramebufferAttachment mColorAttachment;
    private FramebufferAttachment[] mAttachments;

    public Framebuffer(int handle, FramebufferAttachment colorAttachment, FramebufferAttachment... attachments) {
        mHandle = handle;
        mColorAttachment = colorAttachment;
        mAttachments = attachments;
    }

    public int getHandle() {
        return mHandle;
    }

    public int getWidth() {
        return getTexture().getWidth();
    }

    public int getHeight() {
        return getTexture().getHeight();
    }

    public Texture2D getTexture() {
        return mColorAttachment.getTexture();
    }

    public FramebufferAttachment[] getAttachments() {
        return mAttachments;
    }
}
