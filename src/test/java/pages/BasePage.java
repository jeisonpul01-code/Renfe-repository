package pages;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.log = LogManager.getLogger(getClass());
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void waitForVisibility(WebElement element) {
        waitForVisibility(element, true);
    }

    public void waitForVisibility(WebElement element, boolean shouldFail) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            if (shouldFail) {
                throw new RuntimeException("Element not visible and indicated shouldFail=true", e);
            }
        }
    }

    public void waitAndClick(WebElement element) {
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(element));
        clickable.click();
    }

    public void waitSeconds(long seconds) {
        if (seconds <= 0) return;
        try {
            Thread.sleep(java.time.Duration.ofSeconds(seconds).toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted wait", e);
        }
    }

    public void type(WebElement element, String text, boolean clearFirst) {
        waitForVisibility(element, true);
        if (text == null) text = "";
        if (clearFirst) {
            element.clear();
        }
        element.sendKeys(text);
    }

    public  void confirmAutocompleteSelection(WebElement input) {
        wait.until(ExpectedConditions.visibilityOf(input));
        input.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
    }
}
