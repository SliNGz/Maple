package com.maple.content.loaders;

import com.maple.content.IContentLoader;
import com.maple.content.exceptions.Texture2DLoadFailedException;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.texture.Texture2DCreator;
import com.maple.graphics.texture.exceptions.STBImageLoadFailedException;
import com.maple.graphics.texture.exceptions.Texture2DFormatNotSupported;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture2DLoader implements IContentLoader<Texture2D> {
    private Texture2DCreator mTexture2DCreator;

    public Texture2DLoader(Texture2DCreator texture2DCreator) {
        mTexture2DCreator = texture2DCreator;
    }

    public Texture2D load(String path) {
        try {
            IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
            IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

            ByteBuffer data = STBImage.stbi_load(path, widthBuffer, heightBuffer, channelsBuffer, STBI_default);
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

                default:
                    throw new Texture2DFormatNotSupported();
            }

            return mTexture2DCreator.create(width, height, internalFormat, dataFormat, data);
        } catch (Throwable throwable) {
            throw new Texture2DLoadFailedException(throwable);
        }
    }

    public void unload(Texture2D texture) {
        mTexture2DCreator.destroy(texture);
    }
}
