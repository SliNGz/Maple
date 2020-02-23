package com.maple.game;

import com.maple.graphics.shader.binder.IShaderBinder;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;

public class GameContext {
    private Window mWindow;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IShaderBinder mShaderBinder;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    public GameContext(Window window, IKeymap keymap, IShaderManager shaderManager, IShaderBinder shaderBinder,
                       IMousePositionCallbackDispatcher mousePositionCallbackDispatcher) {
        mWindow = window;
        mKeymap = keymap;
        mShaderManager = shaderManager;
        mShaderBinder = shaderBinder;
        mMousePositionCallbackDispatcher = mousePositionCallbackDispatcher;
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

    public IMousePositionCallbackDispatcher getMousePositionCallbackDispatcher() {
        return mMousePositionCallbackDispatcher;
    }
}
