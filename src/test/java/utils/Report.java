package utils;

import listeners.ExtentTestNGListener;

public final class Report {

    private Report() {}

    public static void step(String message, Object... args) {
        if (ExtentTestNGListener.getTest() == null) return;

        String fmt = message.replace("{}", "%s");
        String msg = (args == null || args.length == 0) ? message : String.format(fmt, args);

        ExtentTestNGListener.getTest().info(msg);
    }

    public static void pass(String message, Object... args) {
        if (ExtentTestNGListener.getTest() == null) return;

        String fmt = message.replace("{}", "%s");
        String msg = (args == null || args.length == 0) ? message : String.format(fmt, args);

        ExtentTestNGListener.getTest().pass(msg);
    }

    public static void fail(String message, Object... args) {
        if (ExtentTestNGListener.getTest() == null) return;

        String fmt = message.replace("{}", "%s");
        String msg = (args == null || args.length == 0) ? message : String.format(fmt, args);

        ExtentTestNGListener.getTest().fail(msg);
    }
}