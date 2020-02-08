package com.maple.game;

import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.glfw.GLFWHelper;
import com.maple.glfw.exceptions.GLFWInitializationFailedException;
import com.maple.glfw.exceptions.MonitorRetrievalFailedException;
import com.maple.glfw.exceptions.VideoModeRetrievalFailedException;
import com.maple.glfw.exceptions.WindowCreationFailedException;
import com.maple.graphics.monitor.Monitor;
import com.maple.graphics.window.Window;
import com.maple.graphics.window.WindowCreationProperties;
import com.maple.input.keyboard.KeyCallback;
import com.maple.input.keyboard.KeyboardState;
import com.maple.input.keyboard.KeyboardUpdater;
import com.maple.input.keyboard.Keymap;
import com.maple.log.Logger;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class MapleGame implements IGame {
    private final IGameCreator mGameCreator;
    private final GameProperties mGameProperties;

    private Window mWindow;

    private KeyboardUpdater mKeyboardUpdater;

    private IGame mGame;

    public MapleGame(IGameCreator gameCreator, GameProperties gameProperties) {
        mGameCreator = gameCreator;
        mGameProperties = gameProperties;
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setLogger(mGameProperties.getLogWriter());

        try {
            GLFWHelper.initialize();

            Logger.infoCore("GLFW_INITIALIZED");
        } catch (GLFWInitializationFailedException e) {
            Logger.errorCore("GLFW_INITIALIZATION_FAILED");
            throw new OperationFailedException(e);
        }

        try {
            Monitor monitor = GLFWHelper.getPrimaryMonitor();
            GLFWVidMode videoMode = GLFWHelper.getVideoMode(monitor);
            mWindow = createWindow(videoMode, mGameProperties.getWindowCreationProperties());

            KeyboardState keyboardState = new KeyboardState();
            mKeyboardUpdater = new KeyboardUpdater(keyboardState, new Keymap());

            GLFWHelper.setWindowKeyCallback(mWindow, new KeyCallback(keyboardState));

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

        GL.createCapabilities();

        GameContext gameContext = new GameContext(mWindow, mKeyboardUpdater.getKeymap());
        mGame = mGameCreator.create(gameContext);
        mGame.initialize();
    }

    @Override
    public void update(GameTime gameTime) {
        mKeyboardUpdater.update();
        mGame.update(gameTime);
    }

    @Override
    public void render() {
        mGame.render();
        GLFWHelper.swapBuffers(mWindow);
    }

    @Override
    public void cleanup() {
        mGame.cleanup();

        GLFWHelper.destroyWindow(mWindow);
        GLFWHelper.terminate();
        Logger.clearLogger();
    }

    @Override
    public boolean shouldExit() {
        return mGame.shouldExit() || GLFWHelper.shouldCloseWindow(mWindow);
    }

    private Window createWindow(GLFWVidMode videoMode, WindowCreationProperties creationProperties) throws WindowCreationFailedException {
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        Window window = GLFWHelper.createWindow(creationProperties);

        int x = (videoMode.width() - window.getWidth()) / 2;
        int y = (videoMode.height() - window.getHeight()) / 2;
        window.setPosition(x, y);

        GLFWHelper.makeContextCurrent(window);
        GLFWHelper.showWindow(window);

        return window;
    }
}
