package base;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest extends BaseSuite {
    protected WebDriver driver;
    protected Logger log = LogManager.getLogger(getClass());

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
