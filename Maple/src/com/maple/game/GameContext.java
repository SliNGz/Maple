package com.maple.game;

import com.maple.content.ContentLoader;
import com.maple.entity.IEntityManager;
import com.maple.graphics.GraphicsManager;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;
import com.maple.utils.IFramesCounter;

public class GameContext {
    private IFramesCounter mFramesCounter;
    private GraphicsManager mGraphicsManager;
    private IKeymap mKeymap;
    private ContentLoader mContentLoader;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;
    private IEntityManager mEntityManager;

    public GameContext(IFramesCounter framesCounter, GraphicsManager graphicsManager, IKeymap keymap, ContentLoader contentLoader,
                       IMousePositionCallbackDispatcher mousePositionCallbackDispatcher, IEntityManager entityManager) {
        mFramesCounter = framesCounter;
        mGraphicsManager = graphicsManager;
        mKeymap = keymap;
        mContentLoader = contentLoader;
        mMousePositionCallbackDispatcher = mousePositionCallbackDispatcher;
        mEntityManager = entityManager;
    }

    public IFramesCounter getFramesCounter() {
        return mFramesCounter;
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
