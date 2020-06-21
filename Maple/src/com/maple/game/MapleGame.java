package com.maple.game;

import com.maple.content.ContentLoader;
import com.maple.content.loaders.ShaderLoader;
import com.maple.content.loaders.Texture2DLoader;
import com.maple.entity.EntityManager;
import com.maple.entity.component.EntityComponentManagers;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GLFWHelper;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.buffer.vertex.format.VertexFormatBinder;
import com.maple.graphics.exceptions.GLFWInitializationFailedException;
import com.maple.graphics.exceptions.MonitorRetrievalFailedException;
import com.maple.graphics.exceptions.VideoModeRetrievalFailedException;
import com.maple.graphics.exceptions.WindowCreationFailedException;
import com.maple.graphics.framebuffer.FramebufferCreator;
import com.maple.graphics.monitor.Monitor;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderCreator;
import com.maple.graphics.shader.binder.ShaderBinderCreator;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.texture.Texture2DCreator;
import com.maple.graphics.texture.parameters.TextureParametersSetter;
import com.maple.graphics.window.Window;
import com.maple.input.InputModeCallbackDispatcher;
import com.maple.input.keyboard.KeyCallback;
import com.maple.input.keyboard.KeyboardUpdater;
import com.maple.input.keyboard.map.Keymap;
import com.maple.input.keyboard.state.KeyboardState;
import com.maple.input.mouse.MousePositionCallbackDispatcher;
import com.maple.log.Logger;
import com.maple.math.Vector2f;
import com.maple.renderer.Renderer;
import com.maple.renderer.RendererCreator;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.mesh.quad.PositionTextureQuadMeshCreator;
import com.maple.renderer.mesh.terrain.TerrainIndicesBufferCreator;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.renderer.mesh.terrain.TerrainPositionBufferCreator;
import com.maple.renderer.sprite.SpriteRenderer;
import com.maple.renderer.sprite.shader.SpriteVertexShader;
import com.maple.utils.IFramesCounter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class MapleGame implements IGame {
    private final IGameCreator mGameCreator;
    private final GameProperties mGameProperties;

    private Window mWindow;
    private RendererCreator mRendererCreator;
    private Renderer mRenderer;
    private SpriteRenderer mSpriteRenderer;
    private Texture2DCreator mTexture2DCreator;
    private GraphicsManager mGraphicsManager;
    private EntityManager mEntityManager;

    private Keymap mKeymap;
    private ContentLoader mContentLoader;
    private MousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    private KeyboardUpdater mKeyboardUpdater;

    private IGame mGame;

    private IFramesCounter mFramesCounter;

    public MapleGame(IGameCreator gameCreator, GameProperties gameProperties, IFramesCounter framesCounter) {
        mGameCreator = gameCreator;
        mGameProperties = gameProperties;
        mFramesCounter = framesCounter;
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setLogger(mGameProperties.getLogWriter());

        GLFWErrorCallback.createPrint(System.err).set();

        initializeGLFW();
        initializeWindow();
        initializeGL();
        initializeKeyboard();
        initializeMouse();
        initializeTexture2DCreator();
        initializeContentLoader();
        initializeRenderer();
        initializeGraphicsManager();
        initializeEntityManager();

        GameContext gameContext = new GameContext(mFramesCounter, mGraphicsManager, mKeymap, mContentLoader,
                                                  mMousePositionCallbackDispatcher, mEntityManager);
        mGame = mGameCreator.create(gameContext);
        mGame.initialize();
    }

    @Override
    public void update(GameTime gameTime) {
        mEntityManager.update();
        mKeyboardUpdater.update();
        mGame.update(gameTime);
    }

    @Override
    public void render(float alpha) {
        mGame.render(alpha);
        GLFWHelper.swapBuffers(mWindow);
    }

    @Override
    public void cleanup() {
        mGame.cleanup();

        mEntityManager.cleanup();

        cleanRenderer();
        cleanContentLoader();

        GLFWHelper.freeCallbacks(mWindow);
        GLFWHelper.destroyWindow(mWindow);
        GLFWHelper.terminate();
        GLFWHelper.freeErrorCallback();

        Logger.clearLogger();
    }

    @Override
    public boolean shouldExit() {
        return mGame.shouldExit() || GLFWHelper.shouldCloseWindow(mWindow);
    }

    private void initializeGLFW() throws OperationFailedException {
        try {
            GLFWHelper.initialize();

            Logger.infoCore("GLFW_INITIALIZED");
        } catch (GLFWInitializationFailedException e) {
            Logger.errorCore("GLFW_INITIALIZATION_FAILED");
            throw new OperationFailedException(e);
        }
    }

    private void initializeWindow() throws OperationFailedException {
        try {
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

            mWindow = GLFWHelper.createWindow(mGameProperties.getWindowCreationProperties());

            Monitor monitor = GLFWHelper.getPrimaryMonitor();
            GLFWVidMode videoMode = GLFWHelper.getVideoMode(monitor);
            int x = (videoMode.width() - mWindow.getWidth()) / 2;
            int y = (videoMode.height() - mWindow.getHeight()) / 2;
            mWindow.setPosition(x, y);

            GLFWHelper.makeContextCurrent(mWindow);
            GLFWHelper.showWindow(mWindow);
            GLFWHelper.setSwapInterval(0);

            Logger.infoCore("WINDOW_CREATED");
        } catch (MonitorRetrievalFailedException e) {
            Logger.errorCore("MONITOR_RETRIEVAL_FAILED");
            throw new OperationFailedException(e);
        } catch (VideoModeRetrievalFailedException e) {
            Logger.errorCore("VIDEO_MODE_RETRIEVAL_FAILED");
            throw new OperationFailedException(e);
        } catch (WindowCreationFailedException e) {
            Logger.errorCore("WINDOW_CREATION_FAILED");
            throw new OperationFailedException(e);
        }
    }

    private void initializeGL() {
        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback(System.err);
    }

    private void initializeKeyboard() {
        KeyboardState keyboardState = new KeyboardState();
        mKeymap = new Keymap();
        mKeyboardUpdater = new KeyboardUpdater(keyboardState, mKeymap);

        GLFWHelper.setWindowKeyCallback(mWindow, new KeyCallback(keyboardState));
    }

    private void initializeMouse() {
        GLFWHelper.setCursorPosition(mWindow, new Vector2f(0, 0));

        mMousePositionCallbackDispatcher = new MousePositionCallbackDispatcher(mWindow);
        GLFWHelper.setWindowCursorPositionCallback(mWindow, mMousePositionCallbackDispatcher);
        InputModeCallbackDispatcher.addCallback(mMousePositionCallbackDispatcher);

        GLFWHelper.setWindowInputMode(mWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        GLFWHelper.setWindowInputMode(mWindow, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
    }

    private void initializeTexture2DCreator() {
        stbi_set_flip_vertically_on_load(true);
        TextureParametersSetter textureParametersSetter = new TextureParametersSetter();
        mTexture2DCreator = new Texture2DCreator(textureParametersSetter);
    }

    private void initializeContentLoader() {
        mContentLoader = new ContentLoader(mGameProperties.getContentFolder());
        mContentLoader.addContentLoader(IShader.class, new ShaderLoader(new ShaderCreator()));
        mContentLoader.addContentLoader(Texture2D.class, new Texture2DLoader(mTexture2DCreator));
    }

    private void cleanContentLoader() {
        mContentLoader.cleanup();
    }

    private void initializeRenderer() {
        ShaderBinderCreator shaderBinderCreator = new ShaderBinderCreator();
        mRendererCreator = new RendererCreator(shaderBinderCreator);

        mRenderer = mRendererCreator.create();
    }

    private void cleanRenderer() {
        mRendererCreator.destroy(mRenderer);
    }

    private void initializeGraphicsManager() {
        VertexFormatBinder vertexFormatBinder = new VertexFormatBinder();
        VertexBufferCreator vertexBufferCreator = new VertexBufferCreator();
        VertexArrayCreator vertexArrayCreator = new VertexArrayCreator(vertexFormatBinder, vertexBufferCreator);
        IndexBufferCreator indexBufferCreator = new IndexBufferCreator();

        TerrainPositionBufferCreator positionBufferCreator = new TerrainPositionBufferCreator(vertexBufferCreator);
        TerrainIndicesBufferCreator terrainIndicesBufferCreator = new TerrainIndicesBufferCreator(indexBufferCreator);
        TerrainMeshCreator terrainMeshCreator = new TerrainMeshCreator(vertexArrayCreator,
                                                                       positionBufferCreator,
                                                                       terrainIndicesBufferCreator);

        SpriteVertexShader spriteVertexShader = new SpriteVertexShader(mContentLoader.load(IShader.class, "sprite_vertex_shader.vs"));
        IShader spriteFragmentShader = mContentLoader.load(IShader.class, "sprite_fragment_shader.fs");

        PositionTextureQuadMeshCreator positionTextureQuadMeshCreator = new PositionTextureQuadMeshCreator(vertexBufferCreator,
                                                                                                           vertexArrayCreator,
                                                                                                           indexBufferCreator);
        Mesh quadMesh = positionTextureQuadMeshCreator.create();

        mSpriteRenderer = new SpriteRenderer(mRenderer, spriteVertexShader, spriteFragmentShader, quadMesh);

        FramebufferCreator framebufferCreator = new FramebufferCreator();

        mGraphicsManager = new GraphicsManager(mWindow,
                                               vertexArrayCreator,
                                               vertexBufferCreator,
                                               indexBufferCreator,
                                               mRenderer,
                                               mSpriteRenderer,
                                               terrainMeshCreator,
                                               mTexture2DCreator,
                                               framebufferCreator);
    }

    private void initializeEntityManager() {
        EntityComponentManagers entityComponentManagers = new EntityComponentManagers();
        mEntityManager = new EntityManager(entityComponentManagers);
    }
}
