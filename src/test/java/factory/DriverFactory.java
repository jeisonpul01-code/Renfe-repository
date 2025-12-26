package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    public static WebDriver initializeDriver() {

        // 1) Prevents an old property from forcing an old chromedriver
        System.clearProperty("webdriver.chrome.driver");

        // 2) Clear caches to prevent WDM from reusing old resolutions/drivers
        WebDriverManager.chromedriver()
                .clearResolutionCache()
                .clearDriverCache()
                .setup();

        return new ChromeDriver();
    }
}
