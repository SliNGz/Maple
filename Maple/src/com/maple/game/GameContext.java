package com.maple.game;

import com.maple.graphics.window.Window;

public class GameContext {
    private Window mWindow;

    public GameContext(Window mWindow) {
        this.mWindow = mWindow;
    }

    public Window getWindow() {
        return mWindow;
    }
}
