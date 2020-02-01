package com.maple.game.properties;

import com.maple.log.writer.ILogWriter;

public class GameProperties {
    private final ILogWriter mLogWriter;
    private final int mTickRate;

    public GameProperties(ILogWriter logWriter, int tickRate) {
        mLogWriter = logWriter;
        mTickRate = tickRate;
    }

    public ILogWriter getLogWriter() {
        return mLogWriter;
    }

    public int getTickRate() {
        return mTickRate;
    }
}
