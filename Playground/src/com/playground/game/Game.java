package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.log.Logger;

import static org.lwjgl.opengl.GL11.*;

public class Game implements IGame {
    private GameContext mContext;

    public Game(GameContext context) {
        mContext = context;
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setApplicationTag("Playground");
    }

    @Override
    public void update(GameTime gameTime) {
    }

    @Override
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.23f, 0.11f, 0.11f, 1);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
