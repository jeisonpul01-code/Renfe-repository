package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.AssertExt;
import utils.Report;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[id='onetrust-accept-btn-handler']")
    private WebElement acceptAllCookiesButton;

    @FindBy(css = "input#origin")
    private WebElement originInput;

    public void NavigateToRenfeHomePage() {
        log.info("Navigating to Renfe");
        Report.step("Navigating to Renfe");
        driver.get("https://www.renfe.com/es/es");
        waitForVisibility(originInput, false);
    }

    public void AcceptAllCookies() {
        log.info("Accepting cookies");
        Report.step("Accepting cookies");
        waitForVisibility(acceptAllCookiesButton, false);
        if (acceptAllCookiesButton.isDisplayed()) {
            waitAndClick(acceptAllCookiesButton);
        }
    }

    public void EnterOrigin(String origin) {
        log.info("writing origin point");
        Report.step("writing origin point");
        type(originInput, origin, true);
    }

    public void selectFirstAutocompleteOption() {
        log.info("selecting the first autocomplete option");
        Report.step("selecting the first autocomplete option");
        confirmAutocompleteSelection(originInput);
    }

    public void assertOriginIsMadridAtochaCercanias() {
        log.info("verifying that the point of origin is Madrid-Atocha Cercanias");
        Report.step("verifying that the point of origin is Madrid-Atocha Cercanias");
        AssertExt.assertEquals(
                originInput.getAttribute("value"),
                "MADRID-ATOCHA CERCANÍAS",
                "The selected origin must be MADRID-ATOCHA CERCANÍAS");
    }

}