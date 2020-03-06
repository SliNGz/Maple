package com.maple.graphics;

import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.window.Window;
import com.maple.renderer.Renderer;

public class GraphicsManager {
    private Window mWindow;
    private VertexArrayCreator mVertexArrayCreator;
    private IndexBufferCreator mIndexBufferCreator;
    private Renderer mRenderer;

    public GraphicsManager(Window window,
                           VertexArrayCreator vertexArrayCreator,
                           IndexBufferCreator indexBufferCreator,
                           Renderer renderer) {
        mWindow = window;
        mVertexArrayCreator = vertexArrayCreator;
        mIndexBufferCreator = indexBufferCreator;
        mRenderer = renderer;
    }

    public Window getWindow() {
        return mWindow;
    }

    public VertexArrayCreator getVertexArrayCreator() {
        return mVertexArrayCreator;
    }

    public IndexBufferCreator getIndexBufferCreator() {
        return mIndexBufferCreator;
    }

    public Renderer getRenderer() {
        return mRenderer;
    }
}
