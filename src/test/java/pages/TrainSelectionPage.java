package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Report;

import java.util.List;
import java.util.NoSuchElementException;

public class TrainSelectionPage extends BasePage {

    public TrainSelectionPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.row.selectedTren")
    private List<WebElement> outboundTrainRows;

    @FindBy(css = "span.precio-final[title^='Precio desde']")
    private List<WebElement> priceCells;

    @FindBy(css = "span.text-number[aria-hidden='true']")
    private List<WebElement> durationCells;

    @FindBy(css = "div.tarifaBasica")
    private List<WebElement> basicFareCards;

    @FindBy(css = "p[id='aceptarConfirmacionFareUpgrade']")
    private WebElement continueWithBasicFareButton;

    @FindBy(css = "button[id='btnSeleccionar'][class='select-more']")
    private WebElement selectButton;

   public double selectFirstTrainWithPriceBetween(double minPrice, double maxPrice) {
       log.info("Select first train with price between {}€ and {}€", minPrice, maxPrice);
       Report.step("Select first train with price between {}€ and {}€", minPrice, maxPrice);

       By priceLocator = By.cssSelector("span.precio-final[title^='Precio desde']");
       waitUntilElementsPresent(priceLocator);

       WebElement priceEl = driver.findElements(priceLocator).stream()
               .filter(WebElement::isDisplayed)
               .filter(e -> {
                   String title = e.getAttribute("title");
                   double price = parseSpanishEuro(title);
                   return price >= minPrice && price <= maxPrice;
               })
               .findFirst()
               .orElseThrow(() -> new NoSuchElementException(
                       "No train was found with a price between " + minPrice + "€ y " + maxPrice + "€"
               ));

       double selectedPrice = parseSpanishEuro(priceEl.getAttribute("title"));
       log.info("Train selected with price {}€", selectedPrice);
       Report.step("Train selected with price {}€", selectedPrice);

       WebElement row = priceEl.findElement(By.xpath("./ancestor::div[contains(@class,'row')][1]"));
       scrollToElement(row);
       waitAndClick(row);

       return selectedPrice;
   }

    public void waitResultsLoaded() {
        log.info("Waiting results list to be loaded");
        Report.step("Waiting results list to be loaded");
        wait.until(d -> d.getCurrentUrl().contains("venta.renfe.com"));
        wait.until(d -> !outboundTrainRows.isEmpty());
        wait.until(d -> !priceCells.isEmpty());
        wait.until(d -> priceCells.get(0).isDisplayed());
    }

    public int getTrainRowCount() {
        return outboundTrainRows.size();
    }

    public int getPriceCount() {
        return priceCells.size();
    }

    public int getDurationCount() {
        return durationCells.size();
    }

    public void selectBasicFareForSelectedTrain() {
        log.info("Selecting Basic fare for selected train");
        Report.step("Selecting Basic fare for selected train");

        By modalVisible = By.cssSelector("div[id^='modalTrayecto_']:not([aria-hidden='true'])");

        wait.until(d -> !d.findElements(modalVisible).isEmpty());

        WebElement basicCard = wait.until(d -> basicFareCards.stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));

        if (basicCard == null) {
            throw new NoSuchElementException("Basic fare card not found/visible");
        }
        scrollToElement(basicCard);
        waitAndClick(basicCard);
        waitAndClick(selectButton);
    }

    public PassengerDetailsPage clickContinueWithBasicFare() {
        log.info("Clicking continue with basic fare button");
        Report.step("Clicking continue with basic fare button");
        waitAndClick(continueWithBasicFareButton);
        return new PassengerDetailsPage(driver);
    }
}
