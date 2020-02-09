package com.maple.input.keyboard;

import java.util.HashMap;
import java.util.Map;

public class KeyboardState {
    private Map<Integer, KeyState> mStateMap;
    private int mModifiers;

    public KeyboardState() {
        mStateMap = new HashMap<>();
    }

    public KeyState getKeyState(int key) {
        return mStateMap.getOrDefault(key, KeyState.UP);
    }

    public void setKeyState(int key, KeyState keyState) {
        mStateMap.put(key, keyState);
    }

    public int getModifiers() {
        return mModifiers;
    }

    public void setModifiers(int modifiers) {
        mModifiers = modifiers;
    }
}
