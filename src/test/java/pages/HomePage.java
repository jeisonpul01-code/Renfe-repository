package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[id='onetrust-accept-btn-handler']")
    private WebElement acceptAllCookiesButton;

    @FindBy(css = "input#origin")
    private WebElement originInput;

    public void NavigateToRenfeHomePage() {
        driver.get("https://www.renfe.com/es/es");
        waitForVisibility(originInput, false);
    }

    public void AcceptAllCookies() {
        waitForVisibility(acceptAllCookiesButton, false);
        if (acceptAllCookiesButton.isDisplayed()) {
            waitAndClick(acceptAllCookiesButton);
        }
    }

    public void EnterOrigin(String origin) {
        type(originInput, origin, true);
    }

    public void selectFirstAutocompleteOption() {
        confirmAutocompleteSelection(originInput);
    }

    public void assertOriginIsMadridAtochaCercanias() {
        String expected = "MADRID-ATOCHA CERCANÍAS";
        waitForVisibility(originInput, true);
        String actual = originInput.getAttribute("value");
        Assert.assertEquals(actual, expected,
                String.format("Texto esperado: %s, Texto obtenido: %s", expected, actual));
    }

}