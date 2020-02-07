package com.maple.graphics.window;

import com.maple.log.Logger;

import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;

public class Window {
    private long mHandle;

    private int mX;
    private int mY;
    private int mWidth;
    private int mHeight;
    private String mTitle;

    public Window(long handle, int x, int y, int width, int height, String title) {
        mHandle = handle;
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
        mTitle = title;
    }

    public long getHandle() {
        return mHandle;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public void setPosition(int x, int y) {
        glfwSetWindowPos(mHandle, x, y);
        mX = x;
        mY = y;
    }

    public void setX(int x) {
        setPosition(x, mY);
    }

    public void setY(int y) {
        setPosition(mX, y);
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setSize(int width, int height) {
        glfwSetWindowSize(mHandle, width, height);
        mWidth = width;
        mHeight = height;
    }

    public void setWidth(int width) {
        setSize(width, mHeight);
    }

    public void setHeight(int height) {
        setSize(mWidth, height);
    }

    public String getTitle() {
        return mTitle;
    }
}
