package ui.test;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PassengerDetailsPage;
import pages.TrainSelectionPage;
import utils.AssertExt;

public class PurchaseTrainTicketTest extends BaseTest {

    @Test
    public void buyTicketTest() {
        HomePage homePage = new HomePage(driver);

        homePage.navigateToRenfeHomePage();
        homePage.acceptAllCookies();

        //Selecting origin
        homePage.enterOrigin("Madrid-Puerta");
        homePage.selectFirstAutocompleteOptionForOrigin();
        AssertExt.assertEquals(
                homePage.getSelectedOrigin(),
                "MADRID-PUERTA DE ATOCHA-ALMUDENA GRANDES",
                "The selected origin must be MADRID-PUERTA DE ATOCHA-ALMUDENA GRANDES"
        );

        //Selecting destination
        homePage.enterDestination("Barcelona-Sants");
        homePage.selectFirstAutocompleteOptionForDestination();
        AssertExt.assertEquals(
                homePage.getSelectedDestination(),
                "BARCELONA-SANTS",
                "The selected destination must be BARCELONA-SANTS"
        );

        //Configure “One-way” trip And date with price between 50 and 60 euros
        homePage.selectOneWayTrip();
        homePage.selectFirstAvailableDayWithPriceBetween(50, 60, 3);
        homePage.clickAcceptDateButton();
        TrainSelectionPage trainSelectionPage = homePage.clickSearchForTicket();

        //Search for ticket and select first train with price between 50 and 60 euros
        trainSelectionPage.waitResultsLoaded();
        AssertExt.assertEachTrainHasDurationAndPrice(
                trainSelectionPage.getTrainRowCount(),
                trainSelectionPage.getPriceCount(),
                trainSelectionPage.getDurationCount()
        );
        double selectedPrice = trainSelectionPage.selectFirstTrainWithPriceBetween(50, 60);
        AssertExt.assertBetween( selectedPrice,
                50,
                60,
                "Selected train price must be between 50€ and 60€"
        );

        //Selecting basic fare and continue to passenger details page
        trainSelectionPage.selectBasicFareForSelectedTrain();
        PassengerDetailsPage passengerDetailsPage = trainSelectionPage.clickContinueWithBasicFare();
        AssertExt.assertDisplayed(
                passengerDetailsPage.isPassengerDetailsPageDisplayed(),
                "Passenger Details page is displayed"
        );
    }
}
