package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.IGameCreator;

public class GameCreator implements IGameCreator {
    @Override
    public IGame create(GameContext context) {
        return new Game(context);
    }
}
