package ui.test;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class PurchaseTrainTicketTest extends BaseTest {

    @Test
    public void addToCartTest(){
        HomePage homePage = new HomePage(driver);
        homePage.NavigateToRenfePublicSite();
    }
}
