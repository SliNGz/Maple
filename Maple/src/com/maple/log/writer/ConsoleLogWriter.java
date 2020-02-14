package com.maple.log.writer;

import com.maple.utils.ConsoleColors;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ConsoleLogWriter implements ILogWriter {
    @Override
    public void debug(String tag, String message) {
        log(ConsoleColors.FOREGROUND_BLUE, tag, "DEBUG", message);
    }

    @Override
    public void info(String tag, String message) {
        log(ConsoleColors.FOREGROUND_GREEN, tag, "INFO", message);
    }

    @Override
    public void warn(String tag, String message) {
        log(ConsoleColors.FOREGROUND_YELLOW, tag, "WARN", message);
    }

    @Override
    public void error(String tag, String message) {
        log(ConsoleColors.FOREGROUND_RED, tag, "ERROR", message);
    }

    private void log(String color, String tag, String level, String message) {
        LocalTime time = LocalTime.now();
        String logMessage = String.format("%s[%s][%s][%s] %s%s",
                                          color,
                                          time.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)),
                                          tag, level, message,
                                          ConsoleColors.RESET);

        System.out.println(logMessage);
    }
}
