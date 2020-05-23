package com.maple.game;

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
import com.maple.graphics.monitor.Monitor;
import com.maple.graphics.shader.ShaderCreator;
import com.maple.graphics.shader.ShaderLoader;
import com.maple.graphics.shader.binder.ShaderBinderCreator;
import com.maple.graphics.shader.manager.ShaderManager;
import com.maple.graphics.texture.Texture2DCreator;
import com.maple.graphics.texture.Texture2DLoader;
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
import com.maple.renderer.mesh.terrain.TerrainIndicesBufferCreator;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.renderer.mesh.terrain.TerrainPositionBufferCreator;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.glfw.GLFW.*;

public class MapleGame implements IGame {
    private final IGameCreator mGameCreator;
    private final GameProperties mGameProperties;

    private Window mWindow;
    private RendererCreator mRendererCreator;
    private Renderer mRenderer;
    private GraphicsManager mGraphicsManager;

    private Keymap mKeymap;
    private ShaderManager mShaderManager;
    private MousePositionCallbackDispatcher mMousePositionCallbackDispatcher;
    private Texture2DLoader mTexture2DLoader;

    private KeyboardUpdater mKeyboardUpdater;

    private IGame mGame;

    public MapleGame(IGameCreator gameCreator, GameProperties gameProperties) {
        mGameCreator = gameCreator;
        mGameProperties = gameProperties;
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
        initializeShaderManager();
        initializeRenderer();
        initializeGraphicsManager();
        initializeTexture2DLoader();

        GameContext gameContext = new GameContext(mGraphicsManager, mKeymap, mShaderManager,
                                                  mMousePositionCallbackDispatcher, mTexture2DLoader);
        mGame = mGameCreator.create(gameContext);
        mGame.initialize();
    }

    @Override
    public void update(GameTime gameTime) {
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

        cleanRenderer();
        cleanShaderManager();

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

    private void initializeShaderManager() {
        ShaderCreator shaderCreator = new ShaderCreator();
        ShaderLoader shaderLoader = new ShaderLoader(shaderCreator);
        mShaderManager = new ShaderManager(shaderLoader);
    }

    private void cleanShaderManager() {
        mShaderManager.cleanup();
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

        mGraphicsManager = new GraphicsManager(mWindow,
                                               vertexArrayCreator,
                                               vertexBufferCreator,
                                               indexBufferCreator,
                                               mRenderer,
                                               terrainMeshCreator);
    }

    private void initializeTexture2DLoader() {
        Texture2DCreator texture2DCreator = new Texture2DCreator();
        mTexture2DLoader = new Texture2DLoader(texture2DCreator);
    }
}
