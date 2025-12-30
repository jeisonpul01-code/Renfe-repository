package utils;

import listeners.ExtentTestNGListener;

public final class Report {

    private Report() {}

    public static void step(String message, Object... args) {
        logInfo(format(message, args));
    }

    public static void pass(String message, Object... args) {
        if (ExtentTestNGListener.getTest() == null) return;
        ExtentTestNGListener.getTest().pass(format(message, args));
    }

    public static void fail(String message, Object... args) {
        if (ExtentTestNGListener.getTest() == null) return;
        ExtentTestNGListener.getTest().fail(format(message, args));
    }

    public static void info(String message, Object... args) {
        logInfo(format(message, args));
    }

    public static void raw(String message) {
        logInfo(message);
    }

    // ===== Internal helpers =====

    private static void logInfo(String msg) {
        if (ExtentTestNGListener.getTest() == null) return;
        ExtentTestNGListener.getTest().info(msg);
    }

    private static String format(String message, Object... args) {
        if (message == null) return "null";
        String fmt = message.replace("{}", "%s");

        return (args == null || args.length == 0)
                ? message
                : String.format(fmt, args);
    }
}