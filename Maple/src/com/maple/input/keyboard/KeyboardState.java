package com.maple.input.keyboard;

import com.maple.log.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.HashMap;
import java.util.Map;

public class KeyboardState {
    private Map<Integer, KeyState> mStateMap;
    private int mKeyModifiersMask;

    public KeyboardState() {
        mStateMap = new HashMap<>();
    }

    public KeyState getKeyState(int key) {
        return mStateMap.getOrDefault(key, KeyState.UP);
    }

    public void setKeyState(int key, KeyState keyState) {
        mStateMap.put(key, keyState);
    }

    public int getKeyModifiersMask() {
        return mKeyModifiersMask;
    }

    public void setKeyModifiersMask(int keyModifiersMask) {
        mKeyModifiersMask = keyModifiersMask;
    }
}
