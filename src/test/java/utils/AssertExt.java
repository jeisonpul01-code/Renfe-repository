package utils;

import listeners.ExtentTestNGListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
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

    public static void assertEachTrainHasDurationAndPrice(
            int trainCount,
            int priceCount,
            int durationCount
    ) {
        Assert.assertTrue(trainCount > 0, "Expected at least one train result");
        Assert.assertEquals(priceCount, trainCount, "Each train must have a price");
        Assert.assertEquals(durationCount, trainCount, "Each train must have a duration");
    }

    public static void assertBetween(double actual, double min, double max, String message) {
        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().info("ASSERT: " + message);
            ExtentTestNGListener.getTest().info("Expected range: [" + min + ", " + max + "]");
            ExtentTestNGListener.getTest().info("Actual: " + actual);
        }
        Assert.assertTrue(
                actual >= min && actual <= max,
                message + " | actual=" + actual + ", expected=[" + min + "," + max + "]"
        );
        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().pass("ASSERT OK: " + message);
        }
    }

    public static void assertDisplayed(boolean actual, String message) {

        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().info("ASSERT: " + message);
            ExtentTestNGListener.getTest().info("Expected: element should be displayed");
            ExtentTestNGListener.getTest().info("Actual: " + actual);
        }

        Assert.assertTrue(
                actual,
                message + " | expected=displayed, actual=" + actual
        );

        if (ExtentTestNGListener.getTest() != null) {
            ExtentTestNGListener.getTest().pass("ASSERT OK: " + message);
        }
    }
}