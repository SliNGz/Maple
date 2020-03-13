package com.maple.graphics;

import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.window.Window;
import com.maple.renderer.Renderer;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;

public class GraphicsManager {
    private Window mWindow;
    private VertexArrayCreator mVertexArrayCreator;
    private VertexBufferCreator mVertexBufferCreator;
    private IndexBufferCreator mIndexBufferCreator;
    private Renderer mRenderer;
    private TerrainMeshCreator mTerrainMeshCreator;

    public GraphicsManager(Window window,
                           VertexArrayCreator vertexArrayCreator,
                           VertexBufferCreator vertexBufferCreator,
                           IndexBufferCreator indexBufferCreator,
                           Renderer renderer,
                           TerrainMeshCreator terrainMeshCreator) {
        mWindow = window;
        mVertexArrayCreator = vertexArrayCreator;
        mVertexBufferCreator = vertexBufferCreator;
        mIndexBufferCreator = indexBufferCreator;
        mRenderer = renderer;
        mTerrainMeshCreator = terrainMeshCreator;
    }

    public Window getWindow() {
        return mWindow;
    }

    public VertexArrayCreator getVertexArrayCreator() {
        return mVertexArrayCreator;
    }

    public VertexBufferCreator getVertexBufferCreator() {
        return mVertexBufferCreator;
    }

    public IndexBufferCreator getIndexBufferCreator() {
        return mIndexBufferCreator;
    }

    public Renderer getRenderer() {
        return mRenderer;
    }

    public TerrainMeshCreator getTerrainMeshCreator() {
        return mTerrainMeshCreator;
    }
}
