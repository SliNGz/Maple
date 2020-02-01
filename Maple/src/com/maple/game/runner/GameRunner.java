package com.maple.game.runner;

import com.maple.game.IGame;
import com.maple.game.MapleGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.properties.GameProperties;
import com.maple.game.time.GameTime;
import com.maple.log.Logger;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GameRunner {
    private final MapleGame mMapleGame;
    private final int mTickRate;

    public GameRunner(IGame game) {
        mMapleGame = new MapleGame(game);
        GameProperties gameProperties = mMapleGame.getProperties();
        mTickRate = gameProperties.getTickRate();
    }

    public void run() {
        try {
            double currentTime = glfwGetTime();
            double lastTime = currentTime;
            double deltaTime = 0.0;
            double accumulator = 0.0;
            double tick = 1.0 / mTickRate;

            mMapleGame.initialize();
            while (!mMapleGame.shouldExit()) {
                currentTime = glfwGetTime();
                deltaTime = currentTime - lastTime;
                accumulator += deltaTime;
                lastTime = currentTime;

                if (accumulator >= tick) {
                    mMapleGame.update(new GameTime(currentTime, deltaTime));
                    accumulator -= tick;
                }
                mMapleGame.render();
            }
            mMapleGame.cleanup();
        } catch (OperationFailedException e) {
            Logger.errorCore(e.toString());
        } catch (Throwable throwable) {
            Logger.errorCore("UNCAUGHT_EXCEPTION: " + throwable);
        }
    }
}
