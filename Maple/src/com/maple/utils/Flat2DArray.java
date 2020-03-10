package com.maple.utils;

public class Flat2DArray<T> {
    private int mWidth;
    private int mHeight;
    private T[] mData;

    public Flat2DArray(int width, int height) {
        mWidth = width;
        mHeight = height;
        mData = (T[]) new Object[mWidth * mHeight];
    }

    private int getIndex(int x, int y) {
        return x + y * mWidth;
    }

    public T get(int x, int y) {
        return mData[getIndex(x, y)];
    }

    public void set(int x, int y, T value) {
        mData[getIndex(x, y)] = value;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
