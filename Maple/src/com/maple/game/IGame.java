package com.maple.game;

import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;

public interface IGame {
    void initialize() throws OperationFailedException;

    void update(GameTime gameTime);

    void render();

    void cleanup();

    boolean shouldExit();
}
