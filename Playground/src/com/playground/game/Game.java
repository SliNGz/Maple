package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.Keymap;
import com.maple.log.Logger;
import com.maple.math.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Game implements IGame {
    private Window mWindow;
    private Keymap mKeymap;

    public Game(GameContext context) {
        mWindow = context.getWindow();
        mKeymap = context.getKeymap();
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
