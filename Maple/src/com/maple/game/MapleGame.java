package com.maple.game;

import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.properties.GameProperties;
import com.maple.game.time.GameTime;
import com.maple.log.Logger;
import com.maple.utils.GLFWHelper;
import com.maple.utils.exceptions.GLFWInitializationFailedException;

public class MapleGame implements IGame {
    private final IGame mGame;
    private final GameProperties mGameProperties;

    public MapleGame(IGame game) {
        mGame = game;
        mGameProperties = mGame.getProperties();
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setLogger(mGameProperties.getLogWriter());

        try {
            GLFWHelper.initialize();
        } catch (GLFWInitializationFailedException e) {
            Logger.errorCore("GLFW_INITIALIZATION_FAILED");
            throw new OperationFailedException(e);
        }

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
        Logger.clearLogger();
    }

    @Override
    public boolean shouldExit() {
        return mGame.shouldExit();
    }

    @Override
    public GameProperties getProperties() {
        return mGame.getProperties();
    }
}
