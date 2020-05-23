package com.maple.graphics.texture;

import com.maple.graphics.texture.exceptions.STBImageLoadFailedException;
import com.maple.graphics.texture.exceptions.Texture2DFormatNotSupported;
import com.maple.graphics.texture.exceptions.Texture2DLoadFailedException;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.STBI_rgb;
import static org.lwjgl.stb.STBImage.STBI_rgb_alpha;

public class Texture2DLoader {
    private Texture2DCreator mTexture2DCreator;

    public Texture2DLoader(Texture2DCreator texture2DCreator) {
        mTexture2DCreator = texture2DCreator;
    }

    public Texture2D load(File textureFile) throws Texture2DLoadFailedException {
        try {
            IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

            ByteBuffer data = STBImage.stbi_load(textureFile.getPath(), widthBuffer, heightBuffer, channelsBuffer, 4);
            if (data == null) {
                throw new STBImageLoadFailedException(STBImage.stbi_failure_reason());
            }

            int width = widthBuffer.get();
            int height = heightBuffer.get();
            int channels = channelsBuffer.get();

            int internalFormat = 0;
            int dataFormat = 0;

            switch (channels) {
                case STBI_rgb:
                    internalFormat = GL_RGB8;
                    dataFormat = GL_RGB;
                    break;

                case STBI_rgb_alpha:
                    internalFormat = GL_RGBA8;
                    dataFormat = GL_RGBA;
                    break;
            }

            if (internalFormat == 0 || dataFormat == 0) {
                throw new Texture2DFormatNotSupported();
            }

            return mTexture2DCreator.create(width, height, internalFormat, dataFormat, data);
        } catch (Throwable throwable) {
            throw new Texture2DLoadFailedException(throwable);
        }
    }
}
