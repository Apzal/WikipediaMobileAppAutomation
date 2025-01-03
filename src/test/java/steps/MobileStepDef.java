package steps;

import base.BasePage;
import base.DriverContext;
import base.PageInstance;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.AppPage;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public class MobileStepDef {

    private final DriverContext driverContext;
    private final BasePage basePage;
    private final AppPage appPage;

    public MobileStepDef(DriverContext driverContext, PageInstance pageInstance) {
        this.driverContext = driverContext;
        this.driverContext.currentPage = new PageInstance(driverContext);
        basePage = driverContext.currentPage.As(BasePage.class);
        appPage = driverContext.currentPage.As(AppPage.class);
    }

    @Given("I launch the app")
    public void iLaunchTheApp() throws MalformedURLException, URISyntaxException {
        basePage.launchApp();
        appPage.clickOK();
    }

    @And("I scroll down")
    public void iScrollDown() {
        appPage.scrollDown();
    }

    @And("I click on footer icons mylist history and nearby with {int} seconds pause")
    public void iClickOnFooterIconsMylistHistoryAndNearbyWithSecondsPause(int seconds) {
        for (int i = 2; i <= 4; i++) {
            appPage.clickOnFooterIcon(i);
            basePage.pause(seconds);
        }
    }


    @And("I click on the explore icon")
    public void iClickOnTheExploreIcon() {
        appPage.clickOnFooterIcon(1);
    }

    @Then("I scroll to the top and verify first topic as {string}")
    public void iScrollToTheTopAndVerifyFirstTopic(String text) {
        appPage.scrollToTop();
        Assert.assertTrue(appPage.getElementWithText(text).isDisplayed(),
                text+" not displayed");
    }

    @And("I search for {string}")
    public void iSearchFor(String text) {
        appPage.clickHeader();
        appPage.searchText(text);
    }

    @And("I validate the results for {string}")
    public void iValidateTheResultsFor(String text) {
        int count = appPage.getSearchResults(text);
        Assert.assertTrue(count>1,
                "No search result found for text : "+text);
    }

    @And("I clear the search text")
    public void iClearTheSearchText() {
        appPage.clearSearch();
    }

    @Then("I verify I am in home screen")
    public void iVerifyIAmInHomeScreen() {
        Assert.assertTrue(appPage.getElementWithText("Search Wikipedia").isDisplayed(),
                "Search Wikipedia not displayed");
    }

    @And("I navigate to settings page")
    public void iNavigateToSettingsPage() {
        appPage.clickEllipsis();
        appPage.clickSettings();
    }

    @And("I toggle off for all below settings")
    public void iToggleOffForAllBelowSettings(List<String> settings) {
        for(String setting:settings)
            Assert.assertTrue(appPage.toggleOff(setting),setting+" toggle not set to OFF");
    }

    @And("I navigate back to home screen")
    public void iNavigateBackToHomeScreen() {
        appPage.navigateBack();
    }
}
