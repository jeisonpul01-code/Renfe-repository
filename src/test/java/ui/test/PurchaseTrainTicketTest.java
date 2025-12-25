package ui.test;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;

public class PurchaseTrainTicketTest extends BaseTest {

    @Test
    public void buyTicketTest(){
        HomePage homePage = new HomePage(driver);
        homePage.NavigateToRenfeHomePage();
    }
}
