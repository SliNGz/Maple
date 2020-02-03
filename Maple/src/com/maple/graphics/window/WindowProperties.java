package com.maple.graphics.window;

public class WindowProperties {
    private String mTitle;
    private int mWidth;
    private int mHeight;

    public WindowProperties(String title, int width, int height) {
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
