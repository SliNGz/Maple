package com.maple.input.keyboard;

public class KeyboardUpdater {
    private KeyboardState mKeyboardState;
    private Keymap mKeymap;

    public KeyboardUpdater(KeyboardState keyboardState, Keymap keymap) {
        mKeyboardState = keyboardState;
        mKeymap = keymap;
    }

    public void update() {
        int modifiers = mKeyboardState.getModifiers();

        mKeymap.iterate((key, keyAction) -> {
            KeyState keyState = mKeyboardState.getKeyState(key);

            if (keyState == KeyState.UP) {
                keyAction.onKeyUp();
            } else if (keyState == KeyState.DOWN) {
                keyAction.onKeyDown();
            }
        }, modifiers);
    }

    public Keymap getKeymap() {
        return mKeymap;
    }
}
