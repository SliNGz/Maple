package com.maple.graphics;

import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.framebuffer.FramebufferCreator;
import com.maple.graphics.texture.Texture2DCreator;
import com.maple.graphics.window.Window;
import com.maple.renderer.Renderer;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.renderer.sprite.SpriteRenderer;

public class GraphicsManager {
    private Window mWindow;
    private VertexArrayCreator mVertexArrayCreator;
    private VertexBufferCreator mVertexBufferCreator;
    private IndexBufferCreator mIndexBufferCreator;
    private Renderer mRenderer;
    private SpriteRenderer mSpriteRenderer;
    private TerrainMeshCreator mTerrainMeshCreator;
    private Texture2DCreator mTexture2DCreator;
    private FramebufferCreator mFramebufferCreator;

    public GraphicsManager(Window window,
                           VertexArrayCreator vertexArrayCreator,
                           VertexBufferCreator vertexBufferCreator,
                           IndexBufferCreator indexBufferCreator,
                           Renderer renderer,
                           SpriteRenderer spriteRenderer,
                           TerrainMeshCreator terrainMeshCreator,
                           Texture2DCreator texture2DCreator,
                           FramebufferCreator framebufferCreator) {
        mWindow = window;
        mVertexArrayCreator = vertexArrayCreator;
        mVertexBufferCreator = vertexBufferCreator;
        mIndexBufferCreator = indexBufferCreator;
        mRenderer = renderer;
        mSpriteRenderer = spriteRenderer;
        mTerrainMeshCreator = terrainMeshCreator;
        mTexture2DCreator = texture2DCreator;
        mFramebufferCreator = framebufferCreator;
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

    public SpriteRenderer getSpriteRenderer() {
        return mSpriteRenderer;
    }

    public TerrainMeshCreator getTerrainMeshCreator() {
        return mTerrainMeshCreator;
    }

    public Texture2DCreator getTexture2DCreator() {
        return mTexture2DCreator;
    }

    public FramebufferCreator getFramebufferCreator() {
        return mFramebufferCreator;
    }
}
