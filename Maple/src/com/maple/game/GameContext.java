package com.maple.game;

import com.maple.content.ContentLoader;
import com.maple.entity.IEntityManager;
import com.maple.graphics.GraphicsManager;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;

public class GameContext {
    private GraphicsManager mGraphicsManager;
    private IKeymap mKeymap;
    private ContentLoader mContentLoader;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;
    private IEntityManager mEntityManager;

    public GameContext(GraphicsManager graphicsManager, IKeymap keymap, ContentLoader contentLoader,
                       IMousePositionCallbackDispatcher mousePositionCallbackDispatcher, IEntityManager entityManager) {
        mGraphicsManager = graphicsManager;
        mKeymap = keymap;
        mContentLoader = contentLoader;
        mMousePositionCallbackDispatcher = mousePositionCallbackDispatcher;
        mEntityManager = entityManager;
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

    public IEntityManager getEntityManager() {
        return mEntityManager;
    }
}
