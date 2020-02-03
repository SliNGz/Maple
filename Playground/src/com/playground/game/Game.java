package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.log.Logger;

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
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
