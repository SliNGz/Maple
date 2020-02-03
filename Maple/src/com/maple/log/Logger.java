package com.maple.log;

import com.maple.constants.Constants;
import com.maple.log.writer.ILogWriter;
import com.maple.log.writer.StubLogWriter;

public class Logger {
    private static final StubLogWriter STUB_LOG_WRITER = new StubLogWriter();
    private static ILogWriter sLogWriter = STUB_LOG_WRITER;

    private static final String DEFAULT_APPLICATION_TAG = "APP";
    private static String sApplicationTag = DEFAULT_APPLICATION_TAG;

    public static synchronized void setLogger(ILogWriter logWriter) {
        if (logWriter != null) {
            sLogWriter = logWriter;
        } else {
            clearLogger();
        }
    }

    public static synchronized void clearLogger() {
        sLogWriter = STUB_LOG_WRITER;
    }

    public static synchronized void setApplicationTag(String applicationTag) {
        if (applicationTag != null) {
            sApplicationTag = applicationTag;
        } else {
            clearApplicationTag();
        }
    }

    public static synchronized void clearApplicationTag() {
        sApplicationTag = DEFAULT_APPLICATION_TAG;
    }

    public static synchronized void debugCore(String message) {
        sLogWriter.debug(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public static synchronized void debugCore(String message, Throwable throwable) {
        debugCore(createThrowableMessage(message, throwable));
    }

    public static synchronized void infoCore(String message) {
        sLogWriter.info(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public static synchronized void infoCore(String message, Throwable throwable) {
        infoCore(createThrowableMessage(message, throwable));
    }

    public static synchronized void warnCore(String message) {
        sLogWriter.warn(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public static synchronized void warnCore(String message, Throwable throwable) {
        warnCore(createThrowableMessage(message, throwable));
    }

    public static synchronized void errorCore(String message) {
        sLogWriter.error(Constants.ENGINE_NAME.toUpperCase(), message);
    }

    public static synchronized void errorCore(String message, Throwable throwable) {
        errorCore(createThrowableMessage(message, throwable));
    }

    public static synchronized void debug(String message) {
        sLogWriter.debug(sApplicationTag.toUpperCase(), message);
    }

    public static synchronized void debug(String message, Throwable throwable) {
        debug(createThrowableMessage(message, throwable));
    }

    public static synchronized void info(String message) {
        sLogWriter.info(sApplicationTag.toUpperCase(), message);
    }

    public static synchronized void info(String message, Throwable throwable) {
        info(createThrowableMessage(message, throwable));
    }

    public static synchronized void warn(String message) {
        sLogWriter.warn(sApplicationTag.toUpperCase(), message);
    }

    public static synchronized void warn(String message, Throwable throwable) {
        warn(createThrowableMessage(message, throwable));
    }

    public static synchronized void error(String message) {
        sLogWriter.error(sApplicationTag.toUpperCase(), message);
    }

    public static synchronized void error(String message, Throwable throwable) {
        error(createThrowableMessage(message, throwable));
    }

    private static String createThrowableMessage(String message, Throwable throwable) {
        StringBuilder messageBuilder = new StringBuilder(message);

        messageBuilder.append('\n');
        messageBuilder.append(throwable.toString());

        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            messageBuilder.append('\n');
            messageBuilder.append(stackTraceElement.toString());
        }

        return messageBuilder.toString();
    }
}