package com.maple.graphics.framebuffer.attachment;

import com.maple.graphics.texture.Texture2D;

public class FramebufferAttachment {
    private int mAttachment;
    private Texture2D mTexture;

    public FramebufferAttachment(int attachment, Texture2D texture) {
        mAttachment = attachment;
        mTexture = texture;
    }

    public int getAttachment() {
        return mAttachment;
    }

    public Texture2D getTexture() {
        return mTexture;
    }
}
