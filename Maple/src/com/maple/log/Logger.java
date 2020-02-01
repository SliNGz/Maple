package com.maple.log;

import com.maple.constants.Constants;
import com.maple.log.writer.ILogWriter;
import com.maple.log.writer.StubLogWriter;

public class Logger {
    private static final StubLogWriter STUB_LOG_WRITER = new StubLogWriter();
    private static ILogWriter sLogWriter = STUB_LOG_WRITER;

    private static final String DEFAULT_APPLICATION_TAG = "APP";
    private static String sApplicationTag = DEFAULT_APPLICATION_TAG;

    public synchronized static void setLogger(ILogWriter logWriter) {
        if (logWriter != null) {
            sLogWriter = logWriter;
        } else {
            clearLogger();
        }
    }

    public synchronized static void clearLogger() {
        sLogWriter = STUB_LOG_WRITER;
    }

    public synchronized static void setApplicationTag(String applicationTag) {
        if (applicationTag != null) {
            sApplicationTag = applicationTag;
        } else {
            clearApplicationTag();
        }
    }

    public synchronized static void clearApplicationTag() {
        sApplicationTag = DEFAULT_APPLICATION_TAG;
    }

    public synchronized static void debugCore(String message) {
        sLogWriter.debug(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public synchronized static void infoCore(String message) {
        sLogWriter.info(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public synchronized static void warnCore(String message) {
        sLogWriter.warn(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public synchronized static void errorCore(String message) {
        sLogWriter.error(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public synchronized static void debug(String message) {
        sLogWriter.debug(sApplicationTag, message);
    }

    public synchronized static void info(String message) {
        sLogWriter.info(sApplicationTag, message);
    }

    public synchronized static void warn(String message) {
        sLogWriter.warn(sApplicationTag, message);
    }

    public synchronized static void error(String message) {
        sLogWriter.error(sApplicationTag, message);
    }
}