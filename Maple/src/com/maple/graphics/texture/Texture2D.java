package com.maple.graphics.texture;

public class Texture2D {
    private int mHandle;
    private int mWidth;
    private int mHeight;
    private TextureInternalFormat mInternalFormat;
    private PixelDataFormat mDataFormat;
    private PixelDataType mDataType;

    public Texture2D(int handle, int width, int height,
                     TextureInternalFormat internalFormat, PixelDataFormat dataFormat, PixelDataType dataType) {
        mHandle = handle;
        mWidth = width;
        mHeight = height;
        mInternalFormat = internalFormat;
        mDataFormat = dataFormat;
        mDataType = dataType;
    }

    public int getHandle() {
        return mHandle;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public TextureInternalFormat getInternalFormat() {
        return mInternalFormat;
    }

    public PixelDataFormat getDataFormat() {
        return mDataFormat;
    }

    public PixelDataType getDataType() {
        return mDataType;
    }
}
