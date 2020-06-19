package com.maple.graphics.framebuffer;

import com.maple.graphics.framebuffer.exceptions.FramebufferCreationFailedException;
import com.maple.graphics.texture.PixelDataFormat;
import com.maple.graphics.texture.Texture2D;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL30.*;

public class FramebufferCreator {
    private int mColorBufferIndex;

    public Framebuffer create(Texture2D... buffers) {
        int handle = glGenFramebuffers();

        glBindFramebuffer(GL_FRAMEBUFFER, handle);

        mColorBufferIndex = 0;
        Map<FramebufferAttachment, Texture2D> buffersMap = new HashMap<>();
        for (Texture2D buffer : buffers) {
            FramebufferAttachment attachment = getBufferAttachment(buffer);
            glFramebufferTexture2D(GL_FRAMEBUFFER, attachment.getValue(), GL_TEXTURE_2D, buffer.getHandle(), 0);

            buffersMap.put(attachment, buffer);
        }

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new FramebufferCreationFailedException();
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        return new Framebuffer(handle, buffersMap);
    }

    public void destroy(Framebuffer framebuffer) {
        glDeleteFramebuffers(framebuffer.getHandle());
    }

    private FramebufferAttachment getBufferAttachment(Texture2D texture) {
        PixelDataFormat dataFormat = texture.getDataFormat();

        if (dataFormat == PixelDataFormat.STENCIL_INDEX) {
            return FramebufferAttachment.STENCIL_ATTACHMENT;
        } else if (dataFormat == PixelDataFormat.DEPTH_COMPONENT) {
            return FramebufferAttachment.DEPTH_ATTACHMENT;
        } else if (dataFormat == PixelDataFormat.DEPTH_STENCIL) {
            return FramebufferAttachment.DEPTH_STENCIL_ATTACHMENT;
        } else {
            return FramebufferAttachment.COLOR_ATTACHMENTS[mColorBufferIndex++];
        }
    }
}
