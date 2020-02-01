package com.maple.log.writer;

public interface ILogWriter {
    void debug(String tag, String message);

    void info(String tag, String message);

    void warn(String tag, String message);

    void error(String tag, String message);
}
