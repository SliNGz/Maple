package com.playground.game;

import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.properties.GameProperties;
import com.maple.game.time.GameTime;
import com.maple.log.Logger;
import com.maple.log.writer.ConsoleLogWriter;

public class Game implements IGame {
    @Override
    public void initialize() throws OperationFailedException {
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

    @Override
    public GameProperties getProperties() {
        return new GameProperties(new ConsoleLogWriter(), 1);
    }
}
