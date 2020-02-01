package com.playground;

import com.maple.game.runner.GameRunner;
import com.maple.log.Logger;
import com.maple.log.writer.ConsoleLogWriter;
import com.playground.game.Game;

public class Program {
    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner(new Game());
        gameRunner.run();
    }
}
