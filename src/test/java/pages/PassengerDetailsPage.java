package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PassengerDetailsPage extends BasePage {

    public PassengerDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div[class='titulo']")
    private WebElement travelerInformationTitle;

    public boolean isPassengerDetailsPageDisplayed() {
        waitForVisibility(travelerInformationTitle, false);
        return travelerInformationTitle.isDisplayed();
    }
}
