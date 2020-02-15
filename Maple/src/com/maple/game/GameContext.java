package com.maple.game;

import com.maple.graphics.shader.binder.IShaderBinder;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.map.IKeymap;

public class GameContext {
    private Window mWindow;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IShaderBinder mShaderBinder;

    public GameContext(Window window, IKeymap keymap, IShaderManager shaderManager, IShaderBinder shaderBinder) {
        mWindow = window;
        mKeymap = keymap;
        mShaderManager = shaderManager;
        mShaderBinder = shaderBinder;
    }

    public Window getWindow() {
        return mWindow;
    }

    public IKeymap getKeymap() {
        return mKeymap;
    }

    public IShaderManager getShaderManager() {
        return mShaderManager;
    }

    public IShaderBinder getShaderBinder() {
        return mShaderBinder;
    }
}
