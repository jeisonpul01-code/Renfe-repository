package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Report;

import java.util.List;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[id='onetrust-accept-btn-handler']")
    private WebElement acceptAllCookiesButton;

    @FindBy(css = "input#origin")
    private WebElement originInput;

    @FindBy(css = "input[id='destination'][type='text']")
    private WebElement destinationInput;

        @FindBy(css = "input[id='first-input'][type='text']")
    private WebElement firstDateInput;

    @FindBy(css = "label[for='trip-go']")
    private WebElement oneWayTripRadioButton;

    @FindBy(css = "button[type='button'][class='lightpick__apply-action-sub']")
    private WebElement acceptDateButton;

    @FindBy(css = "input[id='trip-input'][type='text']")
    private WebElement oneWayTripInput;

    @FindBy(css = "div.lightpick__day.is-available")
    private List<WebElement> availableDays;

    @FindBy(css = "span.day-price")
    private WebElement price50to60EuroOption;

    @FindBy(css = "section.lightpick")
    private WebElement calendarRoot;

    @FindBy(css = "button[type='button'][class='lightpick__next-action']")
    private WebElement nextMonthRadioButton;

    @FindBy(css = "button[type='submit'][title='Buscar billete']")
    private WebElement searchForTicket;

    public void navigateToRenfeHomePage() {
        log.info("Navigating to Renfe");
        Report.step("Navigating to Renfe");
        driver.get("https://www.renfe.com/es/es");
        waitForVisibility(originInput, false);
    }

    public void acceptAllCookies() {
        log.info("Accepting cookies");
        Report.step("Accepting cookies");
        waitForVisibility(acceptAllCookiesButton, false);
        if (acceptAllCookiesButton.isDisplayed()) {
            waitAndClick(acceptAllCookiesButton);
        }
    }

    public void enterOrigin(String origin) {
        log.info("writing origin point");
        Report.step("writing origin point");
        type(originInput, origin, true);
    }

    public void selectFirstAutocompleteOption(WebElement input) {
        log.info("selecting the first autocomplete option for input: {}", input.getAttribute("id"));
        Report.step("selecting the first autocomplete option");
        confirmFirstAutocompleteSelection(input);
    }

    public void selectFirstAutocompleteOptionForOrigin() {
        log.info("selecting the first autocomplete option for origin");
        Report.step("selecting the first autocomplete option for origin");
        waitForVisibility(originInput, false);
        selectFirstAutocompleteOption(originInput);
    }

    public void selectFirstAutocompleteOptionForDestination() {
        log.info("selecting the first autocomplete option for destination");
        Report.step("selecting the first autocomplete option for destination");
        waitForVisibility(destinationInput, false);
        selectFirstAutocompleteOption(destinationInput);
    }

    public String getSelectedOrigin() {
        return originInput.getAttribute("value");
    }

    public void enterDestination(String destination) {
        log.info("writing destination");
        Report.step("writing destination");
        type(destinationInput, destination, true);
    }

    public String getSelectedDestination() {
        return destinationInput.getAttribute("value");
    }

    public void selectOneWayTrip() {
        log.info("selecting one-way trip");
        Report.step("selecting one-way trip");
        waitAndClick(firstDateInput);
        waitAndClick(oneWayTripRadioButton);
    }

    public void selectFirstAvailableDayWithPriceBetween(int minPrice, int maxPrice, int maxNextMonths) {
        log.info("Select first date with price from {}€ y {}€", minPrice, maxPrice);
        Report.step("Select first date with price from {}€ y {}€", minPrice, maxPrice);

        waitAndClick(oneWayTripInput);
        waitAndClick(oneWayTripRadioButton);
        waitForVisibility(calendarRoot, false);

        for (int month = 0; month <= maxNextMonths; month++) {

            waitUntilAnyAvailableDayHasPrice();

            List<WebElement> days = calendarRoot.findElements(By.cssSelector("div.lightpick__day.is-available"));

            for (WebElement day : days) {
                String cls = day.getAttribute("class");
                if (cls.contains("is-previous-month") || cls.contains("is-next-month")) continue;

                List<WebElement> priceEls = day.findElements(By.cssSelector("span.day-price"));
                if (priceEls.isEmpty()) continue;

                String priceText = priceEls.get(0).getText();
                if (priceText == null || priceText.trim().isEmpty()) continue;

                int price = parseEuroPrice(priceText);
                if (price < 0) continue;

                if (price >= minPrice && price <= maxPrice) {
                    String dayNumber = day.getText().trim();
                    Report.step("Selected day: {} with price {}€", dayNumber, price);
                    log.info("Selected day {} with price {}€", dayNumber, price);

                    try {
                        day.click();
                    } catch (ElementClickInterceptedException e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", day);
                    }
                    return;
                }
            }

            Report.step("There are no valid dates this month, moving forward");
            String prevHtml = calendarRoot.getAttribute("innerHTML");
            waitAndClick(nextMonthRadioButton);
            wait.until(d -> !calendarRoot.getAttribute("innerHTML").equals(prevHtml));
        }

        throw new NoSuchElementException(
                "No date with price found between " + minPrice + "€ y " + maxPrice +
                        "€ after advancing " + maxNextMonths + " months."
        );
    }

    private int parseEuroPrice(String raw) {
        String digits = raw.replaceAll("[^0-9]", "");
        if (digits.isBlank()) {
            throw new IllegalArgumentException("Invalid price: '" + raw + "'");
        }
        return Integer.parseInt(digits);
    }

    public void clickAcceptDateButton() {
        waitAndClick(acceptDateButton);
    }

    public TrainSelectionPage clickSearchForTicket() {
        log.info("Click Search ticket and wait for venta.renfe.com");
        Report.step("Click Search ticket and wait for venta.renfe.com");
        waitAndClick(searchForTicket);
        wait.until(d -> d.getCurrentUrl().contains("venta.renfe.com"));
        return new TrainSelectionPage(driver);
    }

    private void waitUntilAnyAvailableDayHasPrice() {
        wait.until(d -> {
            List<WebElement> days = calendarRoot.findElements(By.cssSelector("div.lightpick__day.is-available"));
            for (WebElement day : days) {
                String cls = day.getAttribute("class");
                if (cls.contains("is-previous-month") || cls.contains("is-next-month")) continue;

                List<WebElement> priceEls = day.findElements(By.cssSelector("span.day-price"));
                if (priceEls.isEmpty()) continue;

                String txt = priceEls.get(0).getText();
                if (txt != null && !txt.trim().isEmpty()) return true;
            }
            return false;
        });
    }
}