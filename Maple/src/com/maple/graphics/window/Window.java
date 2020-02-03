package com.maple.graphics.window;

public class Window {
    private long mHandle;
    private WindowProperties mProperties;

    public Window(long handle, WindowProperties properties) {
        mHandle = handle;
        mProperties = properties;
    }

    long getHandle() {
        return mHandle;
    }

    public String getTitle() {
        return mProperties.getTitle();
    }

    public int getWidth() {
        return mProperties.getWidth();
    }

    public int getHeight() {
        return mProperties.getHeight();
    }
}
