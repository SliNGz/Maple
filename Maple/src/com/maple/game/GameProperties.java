package com.maple.game;

import com.maple.graphics.window.WindowProperties;
import com.maple.log.writer.ILogWriter;

public class GameProperties {
    private final ILogWriter mLogWriter;
    private final WindowProperties mWindowProperties;

    public GameProperties(ILogWriter logWriter, WindowProperties windowProperties) {
        mLogWriter = logWriter;
        mWindowProperties = windowProperties;
    }

    public ILogWriter getLogWriter() {
        return mLogWriter;
    }

    public WindowProperties getWindowProperties() {
        return mWindowProperties;
    }
}
