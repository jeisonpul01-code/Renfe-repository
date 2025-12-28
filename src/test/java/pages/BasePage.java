package pages;


import org.openqa.selenium.*;
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

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.log = LogManager.getLogger(getClass());
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibility(WebElement element) {
        waitForVisibility(element, true);
    }

    protected void waitForVisibility(WebElement element, boolean shouldFail) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            if (shouldFail) {
                throw new RuntimeException("Element not visible and indicated shouldFail=true", e);
            }
        }
    }

    protected void waitAndClick(WebElement element) {
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

    protected void type(WebElement element, String text, boolean clearFirst) {
        waitForVisibility(element, true);
        if (text == null) text = "";
        if (clearFirst) {
            element.clear();
        }
        element.sendKeys(text);
    }

    protected  void confirmFirstAutocompleteSelection(WebElement input) {
        wait.until(ExpectedConditions.visibilityOf(input));
        input.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
    }

    protected void waitUntilElementsPresent(By locator) {
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) {
            if (!driver.findElements(locator).isEmpty()) return;
            try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        }
        throw new TimeoutException("No elements found for locator: " + locator);
    }

    protected void waitUntilUrlContains(String partialUrl) {
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) {
            if (driver.getCurrentUrl().contains(partialUrl)) return;
            try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        }
        throw new TimeoutException("URL did not contain: " + partialUrl + " | Current: " + driver.getCurrentUrl());
    }

    protected double parseSpanishEuro(String raw) {
        String cleaned = raw
                .replace("Precio desde", "")
                .replace("€", "")
                .replaceAll("\\s+", "")
                .replace(".", "")
                .replace(",", ".");
        return Double.parseDouble(cleaned);
    }

    protected void scrollToElement(WebElement element) {
        if (element == null) throw new IllegalArgumentException("element == null");
        int attempts = 0;
        waitForVisibility(element, false);
        while (attempts < 3) {
            try {
                // Scroll to the center of the viewport
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
                // small offset to avoid fixed headers
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -100);");

                wait.until(ExpectedConditions.elementToBeClickable(element));
                new org.openqa.selenium.interactions.Actions(driver).moveToElement(element).perform();
                return;
            } catch (StaleElementReferenceException | ElementNotInteractableException | TimeoutException e) {
                attempts++;
                try { Thread.sleep(250); } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while scrolling to element", ie);
                }
            }
        }
        throw new RuntimeException("No se pudo desplazar al elemento tras " + attempts + " intentos");
    }
}
