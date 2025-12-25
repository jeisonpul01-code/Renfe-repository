package pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void NavigateToRenfePublicSite() {
        driver.get("https://www.renfe.com/es/es");
    }


}