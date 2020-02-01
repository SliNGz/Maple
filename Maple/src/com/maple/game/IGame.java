package com.maple.game;

import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.properties.GameProperties;
import com.maple.game.time.GameTime;

public interface IGame {
    void initialize() throws OperationFailedException;

    void update(GameTime gameTime);

    void render();

    void cleanup();

    boolean shouldExit();

    GameProperties getProperties();
}
