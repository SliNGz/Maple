package com.maple.game;

import com.maple.graphics.GraphicsManager;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.graphics.texture.Texture2DLoader;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;

public class GameContext {
    private GraphicsManager mGraphicsManager;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;
    private Texture2DLoader mTexture2DLoader;

    public GameContext(GraphicsManager graphicsManager, IKeymap keymap, IShaderManager shaderManager,
                       IMousePositionCallbackDispatcher mousePositionCallbackDispatcher, Texture2DLoader texture2DLoader) {
        mGraphicsManager = graphicsManager;
        mKeymap = keymap;
        mShaderManager = shaderManager;
        mMousePositionCallbackDispatcher = mousePositionCallbackDispatcher;
        mTexture2DLoader = texture2DLoader;
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

    public Texture2DLoader getTexture2DLoader() {
        return mTexture2DLoader;
    }
}
