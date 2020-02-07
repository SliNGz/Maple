package com.maple.game;

import com.maple.graphics.window.WindowCreationProperties;
import com.maple.log.writer.ILogWriter;

public class GameProperties {
    private final ILogWriter mLogWriter;
    private final WindowCreationProperties mWindowCreationProperties;

    public GameProperties(ILogWriter logWriter, WindowCreationProperties windowCreationProperties) {
        mLogWriter = logWriter;
        mWindowCreationProperties = windowCreationProperties;
    }

    public ILogWriter getLogWriter() {
        return mLogWriter;
    }

    public WindowCreationProperties getWindowCreationProperties() {
        return mWindowCreationProperties;
    }
}
