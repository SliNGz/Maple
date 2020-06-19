package com.maple.graphics.framebuffer;

import com.maple.graphics.framebuffer.exceptions.FBOBufferNotSetException;
import com.maple.graphics.texture.Texture2D;

import java.util.Map;

public class Framebuffer {
    private int mHandle;
    private Map<FramebufferAttachment, Texture2D> mBuffers;

    public Framebuffer(int handle, Map<FramebufferAttachment, Texture2D> buffers) {
        mHandle = handle;
        mBuffers = buffers;
    }

    public int getHandle() {
        return mHandle;
    }

    public Texture2D getBuffer(FramebufferAttachment attachment) {
        Texture2D buffer = mBuffers.get(attachment);
        if (buffer == null) {
            throw new FBOBufferNotSetException();
        }

        return buffer;
    }

    public Texture2D getColorBuffer() {
        return getBuffer(FramebufferAttachment.COLOR_ATTACHMENTS[0]);
    }

    public Texture2D getDepthBuffer() {
        return getBuffer(FramebufferAttachment.DEPTH_ATTACHMENT);
    }
}
