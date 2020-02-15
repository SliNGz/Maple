package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.shader.binder.IShaderBinder;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.log.Logger;

import static org.lwjgl.opengl.GL11.*;

public class Game implements IGame {
    private Window mWindow;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IShaderBinder mShaderBinder;

    public Game(GameContext context) {
        mWindow = context.getWindow();
        mKeymap = context.getKeymap();
        mShaderManager = context.getShaderManager();
        mShaderBinder = context.getShaderBinder();
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
        glClearColor(1.0F, 0.5F, 0.0F, 1.0F);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
