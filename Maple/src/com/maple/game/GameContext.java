package com.maple.game;

import com.maple.graphics.GraphicsManager;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;

public class GameContext {
    private GraphicsManager mGraphicsManager;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    public GameContext(GraphicsManager graphicsManager, IKeymap keymap, IShaderManager shaderManager,
                       IMousePositionCallbackDispatcher mousePositionCallbackDispatcher) {
        mGraphicsManager = graphicsManager;
        mKeymap = keymap;
        mShaderManager = shaderManager;
        mMousePositionCallbackDispatcher = mousePositionCallbackDispatcher;
    }

    public GraphicsManager getGraphicsManager() {
        return mGraphicsManager;
    }

    public IKeymap getKeymap() {
        return mKeymap;
    }

    public IShaderManager getShaderManager() {
        return mShaderManager;
    }

    public IMousePositionCallbackDispatcher getMousePositionCallbackDispatcher() {
        return mMousePositionCallbackDispatcher;
    }
}
