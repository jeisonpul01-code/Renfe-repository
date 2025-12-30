package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtentTestNGListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // =========================
    // INITIALIZATION
    // =========================
    private static ExtentReports getExtentReports() {
        if (extent == null) {
            synchronized (ExtentTestNGListener.class) {
                if (extent == null) {
                    extent = createExtentReports();
                }
            }
        }
        return extent;
    }

    private static ExtentReports createExtentReports() {
        try {
            Files.createDirectories(Paths.get("reports"));
            Files.createDirectories(Paths.get("logs"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter("reports/TestExecutionReport.html");

        sparkReporter.config().setReportName("Renfe Automation Report");
        sparkReporter.config().setDocumentTitle("Automation Execution");

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(sparkReporter);

        reports.setSystemInfo("Framework", "RenfeFramework");
        reports.setSystemInfo("Execution", "TestNG");

        return reports;
    }

    // =========================
    // ACCESSOR FOR REPORT UTILS
    // =========================
    public static ExtentTest getTest() {
        return test.get();
    }

    // =========================
    // TESTNG CALLBACKS
    // =========================
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                getExtentReports().createTest(
                        result.getMethod().getMethodName(),
                        result.getMethod().getDescription()
                );
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }
}