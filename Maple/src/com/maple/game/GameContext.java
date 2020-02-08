package com.maple.game;

import com.maple.graphics.window.Window;
import com.maple.input.keyboard.Keymap;

public class GameContext {
    private Window mWindow;
    private Keymap mKeymap;

    public GameContext(Window window, Keymap keymap) {
        mWindow = window;
        mKeymap = keymap;
    }

    public Window getWindow() {
        return mWindow;
    }

    public Keymap getKeymap() {
        return mKeymap;
    }
}
