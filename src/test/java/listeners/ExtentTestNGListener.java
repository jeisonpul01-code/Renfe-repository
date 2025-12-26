package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    private static final ExtentReports extent = createExtentReports();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter =
                new ExtentSparkReporter("reports/TestExecutionReport.html");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(reporter);

        extentReports.setSystemInfo("Framework", "RenfeFramework");
        extentReports.setSystemInfo("Browser", "Chrome");

        return extentReports;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        test.set(extent.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (getTest() != null) {
            getTest().pass("PASSED");
        }
        test.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (getTest() != null) {
            getTest().fail(result.getThrowable());
        }
        test.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (getTest() != null) {
            getTest().skip("SKIPPED");
            if (result.getThrowable() != null) {
                getTest().skip(result.getThrowable());
            }
        }
        test.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
