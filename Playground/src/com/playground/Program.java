package com.playground;

import com.maple.game.GameProperties;
import com.maple.game.runner.GameRunner;
import com.maple.graphics.window.WindowCreationProperties;
import com.maple.log.writer.ConsoleLogWriter;
import com.playground.game.GameCreator;

public class Program {
    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner(
                new GameCreator(),
                new GameProperties(new ConsoleLogWriter(), new WindowCreationProperties("Playground", 800, 480)),
                64, 1);
        gameRunner.run();
    }
}
