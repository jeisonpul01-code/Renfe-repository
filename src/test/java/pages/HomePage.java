package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Report;

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

    @FindBy(css = "button[type='button'][class='lightpick__next-action']")
    private WebElement nextMonthRadioButton;

    @FindBy(css = "div.lightpick__day.is-available[data-time='1768431600000'], div.lightpick__day.is-available[data-time='1771110000000']")
    private WebElement departureDay15;

    @FindBy(css = "button[type='button'][class='lightpick__apply-action-sub']")
    private WebElement acceptDateButton;

    @FindBy(css = "input[id='trip-input'][type='text']")
    private WebElement oneWayTripInput;

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

    public void selectValidDate() {
        log.info("selecting a valid date in date picker");
        Report.step("selecting a valid date in date picker");
        waitAndClick(nextMonthRadioButton);
        waitAndClick(departureDay15);
        waitAndClick(acceptDateButton);
    }

    public String getOneWayTripInput() {
        waitForVisibility(oneWayTripInput, false);
        return oneWayTripInput.getAttribute("value");
    }
}