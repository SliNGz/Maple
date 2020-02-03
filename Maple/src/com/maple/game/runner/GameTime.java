package com.maple.game.runner;

public class GameTime {
    private final double mTotalGameTime;
    private final double mDeltaTime;

    public GameTime(double totalGameTime, double deltaTime) {
        mTotalGameTime = totalGameTime;
        mDeltaTime = deltaTime;
    }

    public double getTotalGameTime() {
        return mTotalGameTime;
    }

    public double getDeltaTime() { return mDeltaTime; }
}
