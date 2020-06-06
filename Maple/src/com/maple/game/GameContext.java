package com.maple.game;

import com.maple.content.ContentLoader;
import com.maple.graphics.GraphicsManager;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;

public class GameContext {
    private GraphicsManager mGraphicsManager;
    private IKeymap mKeymap;
    private ContentLoader mContentLoader;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    public GameContext(GraphicsManager graphicsManager, IKeymap keymap, ContentLoader contentLoader,
                       IMousePositionCallbackDispatcher mousePositionCallbackDispatcher) {
        mGraphicsManager = graphicsManager;
        mKeymap = keymap;
        mContentLoader = contentLoader;
        mMousePositionCallbackDispatcher = mousePositionCallbackDispatcher;
    }

    public GraphicsManager getGraphicsManager() {
        return mGraphicsManager;
    }

    public IKeymap getKeymap() {
        return mKeymap;
    }

    public ContentLoader getContentLoader() {
        return mContentLoader;
    }

    public IMousePositionCallbackDispatcher getMousePositionCallbackDispatcher() {
        return mMousePositionCallbackDispatcher;
    }
}
