package com.maple.graphics.texture;

import com.maple.graphics.texture.exceptions.NoBoundTextureException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture2DBinder {
    private static final int NO_BOUND_TEXTURE = 0;

    private int mBoundTextureHandle;
    private Texture2D mBoundTexture;

    public void bind(Texture2D texture) {
        int textureHandle = texture.getHandle();
        if (mBoundTextureHandle != textureHandle) {
            glBindTexture(GL_TEXTURE_2D, textureHandle);
            mBoundTextureHandle = textureHandle;
            mBoundTexture = texture;
        }
    }

    public void unbind() {
        if (mBoundTextureHandle != NO_BOUND_TEXTURE) {
            glBindTexture(GL_TEXTURE_2D, NO_BOUND_TEXTURE);
            mBoundTextureHandle = NO_BOUND_TEXTURE;
            mBoundTexture = null;
        }
    }

    public Texture2D getBoundTexture() throws NoBoundTextureException {
        if (mBoundTexture == null) {
            throw new NoBoundTextureException();
        }

        return mBoundTexture;
    }
}
