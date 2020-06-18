package com.maple.graphics.framebuffer;

import com.maple.graphics.framebuffer.attachment.FramebufferAttachment;
import com.maple.graphics.framebuffer.attachment.FramebufferColorAttachmentCreator;
import com.maple.graphics.framebuffer.exceptions.FramebufferCreationFailedException;
import com.maple.graphics.texture.PixelDataFormat;
import com.maple.graphics.texture.PixelDataType;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.texture.TextureInternalFormat;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL30.*;

public class FramebufferCreator {
    private FramebufferColorAttachmentCreator mColorAttachmentCreator;

    public FramebufferCreator(FramebufferColorAttachmentCreator colorAttachmentCreator) {
        mColorAttachmentCreator = colorAttachmentCreator;
    }

    public Framebuffer create(int width, int height, TextureInternalFormat internalFormat,
                              PixelDataFormat dataFormat, PixelDataType dataType, FramebufferAttachment... attachments) {
        int handle = glGenFramebuffers();

        glBindFramebuffer(GL_FRAMEBUFFER, handle);

        FramebufferAttachment colorAttachment = mColorAttachmentCreator.create(width, height, internalFormat, dataFormat, dataType);

        ArrayList<FramebufferAttachment> attachmentList = new ArrayList<>(Arrays.asList(attachments));
        attachmentList.add(0, colorAttachment);

        for (FramebufferAttachment attachment : attachmentList) {
            Texture2D texture = attachment.getTexture();
            glFramebufferTexture2D(GL_FRAMEBUFFER, attachment.getAttachment(), GL_TEXTURE_2D, texture.getHandle(), 0);
        }

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new FramebufferCreationFailedException();
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        return new Framebuffer(handle, colorAttachment, attachments);
    }

    public void destroy(Framebuffer framebuffer) {
        glDeleteFramebuffers(framebuffer.getHandle());
    }

    public FramebufferColorAttachmentCreator getColorAttachmentCreator() {
        return mColorAttachmentCreator;
    }
}
