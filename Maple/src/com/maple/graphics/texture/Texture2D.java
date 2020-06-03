package com.maple.graphics.texture;

public class Texture2D {
    private int mHandle;
    private int mWidth;
    private int mHeight;

    public Texture2D(int handle, int width, int height) {
        mHandle = handle;
        mWidth = width;
        mHeight = height;
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
}
