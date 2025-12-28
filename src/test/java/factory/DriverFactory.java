package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    public static WebDriver initializeDriver() {

        System.clearProperty("webdriver.chrome.driver");

        WebDriverManager.chromedriver()
                .clearResolutionCache()
                .clearDriverCache()
                .setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-geolocation");

        return new ChromeDriver(options);
    }
}

