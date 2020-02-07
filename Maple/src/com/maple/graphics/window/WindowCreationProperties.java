package com.maple.graphics.window;

public class WindowCreationProperties {
    private String mTitle;
    private int mWidth;
    private int mHeight;

    public WindowCreationProperties(String title, int width, int height) {
        mTitle = title;
        mWidth = width;
        mHeight = height;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
