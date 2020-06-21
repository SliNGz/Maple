package com.maple.game.runner;

import com.maple.game.GameProperties;
import com.maple.game.IGameCreator;
import com.maple.game.MapleGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.log.Logger;
import com.maple.utils.FramesCounter;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class GameRunner {
    private final MapleGame mMapleGame;
    private final int mTickRate;
    private final float mTimeScale;

    private FramesCounter mFramesCounter;

    public GameRunner(IGameCreator gameCreator, GameProperties gameProperties, int tickRate, float timeScale) {
        mFramesCounter = new FramesCounter();
        mMapleGame = new MapleGame(gameCreator, gameProperties, mFramesCounter);
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
            double fpsElapsedTime = 0.0;
            int fps = 0;

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

                fpsElapsedTime += deltaTime;
                ++fps;
                if (fpsElapsedTime > 1.0) {
                    mFramesCounter.setFPS(fps);
                    fpsElapsedTime -= 1.0;
                    fps = 0;
                }
            }
            mMapleGame.cleanup();
        } catch (OperationFailedException e) {
            Logger.errorCore("OPERATION_FAILED_EXCEPTION", e.getCause());
            System.exit(-1);
        } catch (Throwable throwable) {
            Logger.errorCore("UNCAUGHT_EXCEPTION", throwable);
            System.exit(-1);
        }
    }
}
