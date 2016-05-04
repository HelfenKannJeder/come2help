package de.helfenkannjeder.come2help.server.rest.logging;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Joiner;

public class LogSession {
    private static ThreadLocal<Map<String, String>> logMessages = new ThreadLocal<>();
    private static ThreadLocal<Throwable> exceptionToLog = new ThreadLocal<>();

    public static void start() {
        logMessages.set(new LinkedHashMap<>());
    }

    public static void close() {
        logMessages.remove();
        exceptionToLog.remove();
    }

    public static void log(String key, String message) {
        Map<String, String> messages = logMessages.get();
        if(messages != null) {
            messages.put(key, message);
        }
    }

    public static void setExceptionToLog(Throwable exception) {
        exceptionToLog.set(exception);
    }

    public static Throwable getExceptionToLog() {
        return exceptionToLog.get();
    }

    public static String getLogMessage() {
        Map<String, String> messages = logMessages.get();
        if(messages != null) {
            return  Joiner.on(" ").withKeyValueSeparator("=").join(messages);
        }

        return "";
    }
}
