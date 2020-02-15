package com.maple.input.keyboard.map;

import com.maple.input.keyboard.IKeyAction;
import com.maple.input.keyboard.Key;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Keymap implements IKeymap {
    private Map<Integer, IKeyAction> mMap;
    private Set<Integer> mBoundKeycodeSet;

    public Keymap() {
        mMap = new HashMap<>();
        mBoundKeycodeSet = new HashSet<>();
    }

    public void add(Key key, IKeyAction keyAction) {
        mMap.put(key.getModifiedKeycode(), keyAction);
        mBoundKeycodeSet.add(key.getKeycode());
    }

    public IKeyAction get(Key key) {
        return mMap.get(key.getModifiedKeycode());
    }

    private IKeyAction getKeyAction(int keycode, int modifiers) {
        return mMap.getOrDefault(Key.getModifiedKeycode(keycode, modifiers), mMap.get(keycode));
    }

    public void iterate(IKeymapIterator keymapIterator, int modifiers) {
        for (int keycode : mBoundKeycodeSet) {
            IKeyAction keyAction = getKeyAction(keycode, modifiers);
            if (keyAction != null) {
                keymapIterator.invoke(keycode, keyAction);
            }
        }
    }
}
