package com.maple.input.keyboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Keymap {
    private Map<Integer, IKeyAction> mMap;
    private Set<Integer> mBoundKeycodeSet;

    public Keymap() {
        mMap = new HashMap<>();
        mBoundKeycodeSet = new HashSet<>();
    }

    public void add(Key key, IKeyAction keyAction) {
        mBoundKeycodeSet.add(key.getKeycode());
        mMap.put(key.getModifiedKeycode(), keyAction);
    }

    private IKeyAction getKeyAction(int keycode, int modifiers) {
        return mMap.getOrDefault(Key.getModifiedKeycode(keycode, modifiers), mMap.get(keycode));
    }

    void iterate(IKeymapIterator keymapIterator, int modifiers) {
        for (int keycode : mBoundKeycodeSet) {
            IKeyAction keyAction = getKeyAction(keycode, modifiers);
            if (keyAction != null) {
                keymapIterator.invoke(keycode, keyAction);
            }
        }
    }
}
