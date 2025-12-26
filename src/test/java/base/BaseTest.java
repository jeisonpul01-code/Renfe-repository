package base;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseTest {
    protected WebDriver driver;
    protected Logger log = LogManager.getLogger(getClass());

    @BeforeSuite
    public void ensureFolders() throws Exception {
        Files.createDirectories(Paths.get("logs"));
        Files.createDirectories(Paths.get("reports"));
    }

    @BeforeMethod
    public void setUp(){
        driver = DriverFactory.initializeDriver();
        driver.manage().window().maximize();
        log.info("Driver initialized");
    }

    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();
            log.info("Driver closed");
        }
    }
}
