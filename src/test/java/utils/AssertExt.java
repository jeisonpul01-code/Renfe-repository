package utils;

import listeners.ExtentTestNGListener;
import org.testng.Assert;

import java.util.regex.Pattern;

public final class AssertExt {

    private AssertExt() {}

    public static void assertEquals(String actual, String expected, String message) {
        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().info("ASSERT: " + message);
            ExtentTestNGListener.getTest().info("Expected: " + expected);
            ExtentTestNGListener.getTest().info("Actual: " + actual);
        }

        Assert.assertEquals(actual, expected, message);

        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().pass("ASSERT OK: " + message);
        }
    }

    public static void assertMatches(String actual, String regex, String message) {
        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().info("ASSERT (regex): " + message);
            ExtentTestNGListener.getTest().info("Regex: " + regex);
            ExtentTestNGListener.getTest().info("Actual: " + actual);
        }

        boolean matches = actual != null && Pattern.compile(regex).matcher(actual).matches();
        Assert.assertTrue(matches, message + " | Actual: " + actual);

        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().pass("ASSERT OK (regex): " + message);
        }
    }
}