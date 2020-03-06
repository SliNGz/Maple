package com.maple.game;

import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GLFWHelper;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.buffer.BufferBinder;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.format.VertexFormatBinder;
import com.maple.graphics.exceptions.GLFWInitializationFailedException;
import com.maple.graphics.exceptions.MonitorRetrievalFailedException;
import com.maple.graphics.exceptions.VideoModeRetrievalFailedException;
import com.maple.graphics.exceptions.WindowCreationFailedException;
import com.maple.graphics.monitor.Monitor;
import com.maple.graphics.shader.ShaderCreator;
import com.maple.graphics.shader.ShaderLoader;
import com.maple.graphics.shader.binder.ShaderBinder;
import com.maple.graphics.shader.binder.ShaderBinderCreator;
import com.maple.graphics.shader.manager.ShaderManager;
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
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.glfw.GLFW.*;

public class MapleGame implements IGame {
    private final IGameCreator mGameCreator;
    private final GameProperties mGameProperties;

    private Window mWindow;
    private ShaderBinderCreator mShaderBinderCreator;
    private ShaderBinder mShaderBinder;
    private Renderer mRenderer;
    private GraphicsManager mGraphicsManager;

    private Keymap mKeymap;
    private ShaderManager mShaderManager;
    private MousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

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

        GameContext gameContext = new GameContext(mGraphicsManager, mKeymap, mShaderManager,
                                                  mMousePositionCallbackDispatcher);
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

        mShaderBinderCreator.destroy(mShaderBinder);
        mShaderManager.cleanup();

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

    private void initializeRenderer() {
        mShaderBinderCreator = new ShaderBinderCreator();
        mShaderBinder = mShaderBinderCreator.create();

        BufferBinder bufferBinder = new BufferBinder();

        mRenderer = new Renderer(mShaderBinder, bufferBinder);
    }

    private void initializeGraphicsManager() {
        mGraphicsManager = new GraphicsManager(mWindow,
                                               new VertexArrayCreator(new VertexFormatBinder()),
                                               new IndexBufferCreator(),
                                               mRenderer);
    }
}
