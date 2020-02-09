package com.maple.input.keyboard;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Keymap {
    private static final int MOD_NONE = 0;
    private static final int LAST_KEY_HIGHEST_ORDER_BIT = Integer.numberOfTrailingZeros(Integer.highestOneBit(GLFW.GLFW_KEY_LAST));

    private Map<Integer, IKeyAction> mMap;
    private Set<Integer> mBoundKeys;

    public Keymap() {
        mMap = new HashMap<>();
        mBoundKeys = new HashSet<>();
    }

    public void add(int key, int modifiers, IKeyAction keyAction) {
        mBoundKeys.add(key);
        mMap.put(getModifiedKey(key, modifiers), keyAction);
    }

    public void add(int key, IKeyAction keyAction) {
        add(key, MOD_NONE, keyAction);
    }

    private int getModifiedKey(int key, int modifiers) {
        return (modifiers << (LAST_KEY_HIGHEST_ORDER_BIT + 1)) | key;
    }

    private IKeyAction getKeyAction(int key, int modifiers) {
        return mMap.getOrDefault(getModifiedKey(key, modifiers), mMap.get(key));
    }

    void iterate(IKeymapIterator keymapIterator, int modifiers) {
        for (int key : mBoundKeys) {
            IKeyAction keyAction = getKeyAction(key, modifiers);
            if (keyAction != null) {
                keymapIterator.invoke(key, keyAction);
            }
        }
    }
}
