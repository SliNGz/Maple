package com.maple.game.runner;

import com.maple.game.GameProperties;
import com.maple.game.IGameCreator;
import com.maple.game.MapleGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.log.Logger;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class GameRunner {
    private final MapleGame mMapleGame;
    private final int mTickRate;
    private final float mTimeScale;

    public GameRunner(IGameCreator gameCreator, GameProperties gameProperties, int tickRate, float timeScale) {
        mMapleGame = new MapleGame(gameCreator, gameProperties);
        mTickRate = tickRate;
        mTimeScale = timeScale;
    }

    public void run() {
        try {
            double totalTime = 0.0;
            double currentTime = glfwGetTime();
            double lastTime = currentTime;
            double deltaTime = 0.0;
            double accumulator = 0.0;
            double tick = 1.0 / mTickRate;
            double timeScaledTick = tick * mTimeScale;

            mMapleGame.initialize();
            while (!mMapleGame.shouldExit()) {
                currentTime = glfwGetTime();
                deltaTime = currentTime - lastTime;
                lastTime = currentTime;

                accumulator += deltaTime;

                glfwPollEvents();
                while (accumulator >= tick) {
                    mMapleGame.update(new GameTime(totalTime, timeScaledTick));
                    totalTime += timeScaledTick;
                    accumulator -= tick;
                }
                mMapleGame.render((float) (accumulator / tick));
            }
            mMapleGame.cleanup();
        } catch (OperationFailedException e) {
            Logger.errorCore("OPERATION_FAILED_EXCEPTION", e.getCause());
        } catch (Throwable throwable) {
            Logger.errorCore("UNCAUGHT_EXCEPTION", throwable);
        }
    }
}
