package com.maple.input.keyboard;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Keymap {
    private Map<Integer, IKeyAction> mDefaultKeymap;
    private Map<Integer, IKeyAction> mShiftKeymap;
    private Map<Integer, IKeyAction> mCtrlKeymap;
    private Map<Integer, IKeyAction> mAltKeymap;
    private Map<Integer, IKeyAction> mShiftCtrlKeymap;
    private Map<Integer, IKeyAction> mShiftAltKeymap;
    private Map<Integer, IKeyAction> mCtrlAltKeymap;
    private Map<Integer, IKeyAction> mShiftCtrlAltKeymap;

    private Map<Integer, Map<Integer, IKeyAction>> mKeyModifiersMap;

    private Set<Integer> mBoundKeys;

    public Keymap(Map<Integer, IKeyAction> defaultKeymap,
                  Map<Integer, IKeyAction> shiftKeymap,
                  Map<Integer, IKeyAction> ctrlKeymap,
                  Map<Integer, IKeyAction> altKeymap,
                  Map<Integer, IKeyAction> shiftCtrlKeymap,
                  Map<Integer, IKeyAction> shiftAltKeymap,
                  Map<Integer, IKeyAction> ctrlAltKeymap,
                  Map<Integer, IKeyAction> shiftCtrlAltKeymap) {
        mDefaultKeymap = defaultKeymap;
        mShiftKeymap = shiftKeymap;
        mCtrlKeymap = ctrlKeymap;
        mAltKeymap = altKeymap;
        mShiftCtrlKeymap = shiftCtrlKeymap;
        mShiftAltKeymap = shiftAltKeymap;
        mCtrlAltKeymap = ctrlAltKeymap;
        mShiftCtrlAltKeymap = shiftCtrlAltKeymap;

        mKeyModifiersMap = new HashMap<>();
        mKeyModifiersMap.put(0, mDefaultKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_SHIFT, mShiftKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_CONTROL, mCtrlKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_ALT, mAltKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_SHIFT | GLFW.GLFW_MOD_CONTROL, mShiftCtrlKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_SHIFT | GLFW.GLFW_MOD_ALT, mShiftAltKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_CONTROL | GLFW.GLFW_MOD_ALT, mCtrlAltKeymap);
        mKeyModifiersMap.put(GLFW.GLFW_MOD_SHIFT | GLFW.GLFW_MOD_CONTROL | GLFW.GLFW_MOD_ALT, mShiftCtrlAltKeymap);

        mBoundKeys = new HashSet<>();
    }

    public Keymap() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
                new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    private void add(Map<Integer, IKeyAction> map, int key, IKeyAction keyAction) {
        mBoundKeys.add(key);
        map.put(key, keyAction);
    }

    public void add(int key, IKeyAction keyAction) {
        add(mDefaultKeymap, key, keyAction);
    }

    public void addShift(int key, IKeyAction keyAction) {
        add(mShiftKeymap, key, keyAction);
    }

    public void addCtrl(int key, IKeyAction keyAction) {
        add(mCtrlKeymap, key, keyAction);
    }

    public void addAlt(int key, IKeyAction keyAction) {
        add(mAltKeymap, key, keyAction);
    }

    public void addShiftCtrl(int key, IKeyAction keyAction) {
        add(mShiftCtrlKeymap, key, keyAction);
    }

    public void addShiftAlt(int key, IKeyAction keyAction) {
        add(mShiftAltKeymap, key, keyAction);
    }

    public void addCtrlAlt(int key, IKeyAction keyAction) {
        add(mCtrlAltKeymap, key, keyAction);
    }

    public void addShiftCtrlAlt(int key, IKeyAction keyAction) {
        add(mShiftCtrlAltKeymap, key, keyAction);
    }

    private IKeyAction getKeyAction(int key, int keyModifiersMask) {
        Map<Integer, IKeyAction> keymap = mKeyModifiersMap.getOrDefault(keyModifiersMask, mDefaultKeymap);
        return keymap.getOrDefault(key, mDefaultKeymap.get(key));
    }

    void iterate(IKeymapIterator keymapIterator, int keyModifiersMask) {
        for (int key : mBoundKeys) {
            IKeyAction keyAction = getKeyAction(key, keyModifiersMask);
            if (keyAction != null) {
                keymapIterator.invoke(key, keyAction);
            }
        }
    }
}
