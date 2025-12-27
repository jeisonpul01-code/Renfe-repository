package ui.test;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.AssertExt;

public class PurchaseTrainTicketTest extends BaseTest {

    @Test
    public void buyTicketTest() {
        HomePage homePage = new HomePage(driver);

        homePage.navigateToRenfeHomePage();
        homePage.acceptAllCookies();

        //Selecting origin
        homePage.enterOrigin("Madrid-Atocha");
        homePage.selectFirstAutocompleteOptionForOrigin();
        AssertExt.assertEquals(
                homePage.getSelectedOrigin(),
                "MADRID-ATOCHA CERCANÍAS",
                "The selected origin must be MADRID-ATOCHA CERCANÍAS"
        );

        //Selecting destination
        homePage.enterDestination("Barcelona-Sants");
        homePage.selectFirstAutocompleteOptionForDestination();
        AssertExt.assertEquals(
                homePage.getSelectedDestination(),
                "BARCELONA-SANTS",
                "The selected destination must be BARCELONA-SANTS"
        );

        //Configure “One-way” trip
        homePage.selectOneWayTrip();
        homePage.selectValidDate();
        AssertExt.assertMatches(
                homePage.getOneWayTripInput(),
                "^[a-záéíóúñ]{3}\\.?\\,?\\s15/(0[12])/26$",
                "The departure date must be the 15th and have the following format: 'ddd., 15/MM/26' with MM = 01 o 02"
        );

        //Search for ticket and load results


        homePage.waitSeconds(20);
    }
}
