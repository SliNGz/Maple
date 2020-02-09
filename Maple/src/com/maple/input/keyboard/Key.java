package com.maple.input.keyboard;

import org.lwjgl.glfw.GLFW;

public class Key {
    private static final int LAST_KEY_HIGHEST_ORDER_BIT = Integer.numberOfTrailingZeros(Integer.highestOneBit(GLFW.GLFW_KEY_LAST));

    private int mKeycode;
    private int mModifiers;
    private int mModifiedKeycode;

    public Key(int keycode) {
        mKeycode = keycode;
        mModifiers = 0;
        updateModifiedKeycode();
    }

    public int getKeycode() {
        return mKeycode;
    }

    public int getModifiers() {
        return mModifiers;
    }

    public Key addModifier(int modifier) {
        mModifiers |= modifier;
        updateModifiedKeycode();

        return this;
    }

    public int getModifiedKeycode() {
        return mModifiedKeycode;
    }

    private void updateModifiedKeycode() {
        mModifiedKeycode = getModifiedKeycode(mKeycode, mModifiers);
    }

    public static int getModifiedKeycode(int keycode, int modifiers) {
        return (modifiers << (LAST_KEY_HIGHEST_ORDER_BIT + 1)) | keycode;
    }
}
