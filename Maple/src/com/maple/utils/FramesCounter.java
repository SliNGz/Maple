package com.maple.utils;

public class FramesCounter implements IFramesCounter {
    public int mFPS;

    public FramesCounter() {
        mFPS = 0;
    }

    public int getFPS() {
        return mFPS;
    }

    public void setFPS(int fps) {
        mFPS = fps;
    }
}
