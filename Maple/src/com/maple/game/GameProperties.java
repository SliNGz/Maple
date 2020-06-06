package com.maple.game;

import com.maple.graphics.window.WindowCreationProperties;
import com.maple.log.writer.ILogWriter;

import java.io.File;

public class GameProperties {
    private final ILogWriter mLogWriter;
    private final WindowCreationProperties mWindowCreationProperties;
    private final File mContentFolder;

    public GameProperties(ILogWriter logWriter, WindowCreationProperties windowCreationProperties, File contentFolder) {
        mLogWriter = logWriter;
        mWindowCreationProperties = windowCreationProperties;
        mContentFolder = contentFolder;
    }

    public ILogWriter getLogWriter() {
        return mLogWriter;
    }

    public WindowCreationProperties getWindowCreationProperties() {
        return mWindowCreationProperties;
    }

    public File getContentFolder() {
        return mContentFolder;
    }
}
