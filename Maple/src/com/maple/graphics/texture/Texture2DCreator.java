package com.maple.graphics.texture;

import com.maple.graphics.texture.parameters.ITextureParameter;
import com.maple.graphics.texture.parameters.TextureParametersSetter;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;

public class Texture2DCreator {
    private static final int DEFAULT_TEXTURE_LEVEL = 1;

    private static final int DEFAULT_LOD = 0;
    private static final int DEFAULT_XOFFSET = 0;
    private static final int DEFAULT_YOFFSET = 0;

    private TextureParametersSetter mParametersSetter;

    public Texture2DCreator(TextureParametersSetter parametersSetter) {
        mParametersSetter = parametersSetter;
    }

    public Texture2D create(int width, int height, TextureInternalFormat internalFormat,
                            PixelDataFormat dataFormat, PixelDataType dataType, ByteBuffer data,
                            ITextureParameter... parameters) {
        int handle = glCreateTextures(GL_TEXTURE_2D);

        glTextureParameteri(handle, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTextureParameteri(handle, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        mParametersSetter.set(handle, parameters);

        glTextureStorage2D(handle, DEFAULT_TEXTURE_LEVEL, internalFormat.getValue(), width, height);

        if (data != null) {
            glTextureSubImage2D(handle, DEFAULT_LOD, DEFAULT_XOFFSET, DEFAULT_YOFFSET,
                                width, height, dataFormat.getValue(), dataType.getValue(), data);
        }

        return new Texture2D(handle, width, height, internalFormat, dataFormat, dataType);
    }

    public Texture2D create(int width, int height, TextureInternalFormat internalFormat,
                            PixelDataFormat dataFormat, PixelDataType dataType, ITextureParameter... parameters) {
        return create(width, height, internalFormat, dataFormat, dataType, null, parameters);
    }

    public void destroy(Texture2D texture) {
        glDeleteTextures(texture.getHandle());
    }
}
