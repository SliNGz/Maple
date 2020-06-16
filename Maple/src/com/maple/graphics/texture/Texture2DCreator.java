package com.maple.graphics.texture;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture2DCreator {
    public Texture2D create(int width, int height, TextureInternalFormat internalFormat,
                            TexelDataFormat dataFormat, TexelDataType type, ByteBuffer data) {
        int handle = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, handle);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, internalFormat.getValue(), width, height, 0, dataFormat.getValue(), type.getValue(), data);

        glBindTexture(GL_TEXTURE_2D, 0);

        return new Texture2D(handle, width, height);
    }

    public void destroy(Texture2D texture) {
        glDeleteTextures(texture.getHandle());
    }
}
