package utils;

import listeners.ExtentTestNGListener;
import org.testng.Assert;

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
}