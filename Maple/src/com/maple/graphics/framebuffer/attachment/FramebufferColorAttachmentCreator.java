package com.maple.graphics.framebuffer.attachment;

import com.maple.graphics.texture.*;

import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;

public class FramebufferColorAttachmentCreator {
    private Texture2DCreator mTexture2DCreator;

    public FramebufferColorAttachmentCreator(Texture2DCreator texture2DCreator) {
        mTexture2DCreator = texture2DCreator;
    }

    public FramebufferAttachment create(int width, int height, TextureInternalFormat internalFormat,
                                        PixelDataFormat dataFormat, PixelDataType dataType) {
        Texture2D texture = mTexture2DCreator.create(width, height, internalFormat, dataFormat, dataType, null);

        return new FramebufferAttachment(GL_COLOR_ATTACHMENT0, texture);
    }
}
