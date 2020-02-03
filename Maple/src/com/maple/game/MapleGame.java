package com.maple.game;

import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.window.Window;
import com.maple.graphics.window.WindowHelper;
import com.maple.graphics.window.exceptions.WindowCreationFailedException;
import com.maple.log.Logger;
import com.maple.utils.GLFWHelper;
import com.maple.utils.exceptions.GLFWInitializationFailedException;

public class MapleGame implements IGame {
    private final IGameCreator mGameCreator;
    private final GameProperties mGameProperties;

    private GameContext mContext;
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

        Window window = null;
        try {
            window = WindowHelper.create(mGameProperties.getWindowProperties());
            WindowHelper.makeContextCurrent(window);

            Logger.infoCore("WINDOW_CREATED");
        } catch (WindowCreationFailedException e) {
            Logger.errorCore("WINDOW_CREATION_FAILED");
            throw new OperationFailedException(e);
        }

        mContext = new GameContext(window);

        mGame = mGameCreator.create(mContext);
        mGame.initialize();
    }

    @Override
    public void update(GameTime gameTime) {
        mGame.update(gameTime);
    }

    @Override
    public void render() {
        mGame.render();
    }

    @Override
    public void cleanup() {
        mGame.cleanup();

        WindowHelper.destroy(mContext.getWindow());
        GLFWHelper.terminate();
        Logger.clearLogger();
    }

    @Override
    public boolean shouldExit() {
        return mGame.shouldExit();
    }
}
