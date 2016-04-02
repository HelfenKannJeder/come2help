package de.helfenkannjeder.come2help.server.rest.logging;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Joiner;

class LogSession {
    private static ThreadLocal<Map<String, String>> logMessages = new ThreadLocal<>();

    static void start() {
        logMessages.set(new LinkedHashMap<>());
    }

    static void close() {
        logMessages.remove();
    }

    static void log(String key, String message) {
        Map<String, String> messages = logMessages.get();
        if(messages != null) {
            messages.put(key, message);
        }
    }

    static String getLogMessage() {
        Map<String, String> messages = logMessages.get();
        if(messages != null) {
            return  Joiner.on(" ").withKeyValueSeparator("=").join(messages);
        }

        return "";
    }
}
