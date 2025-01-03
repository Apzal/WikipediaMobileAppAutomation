package pages;

import base.BasePage;
import base.DriverContext;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AppPage extends BasePage {


    public AppPage(DriverContext driverContext) {
        super(driverContext);
    }

    public void clickOK(){
        click(By.xpath("//android.widget.Button[@text='OK']"));
    }

    public void clickOnFooterIcon(int index){
        click(By.xpath("(//android.widget.ImageView[@resource-id='org.wikipedia.alpha:id/icon'])["+index+"]"));
    }

    public void scrollToTop(){
        scrollTopTillElement("org.wikipedia.alpha:id/search_container");
    }

    public WebElement getElementWithText(String text){
        return getElement(By.xpath("//android.widget.TextView[@text='"+text+"']"));
    }

    public void searchText(String text){
        setText(By.id("org.wikipedia.alpha:id/search_src_text"),text);
    }

    public void clickHeader(){
        click(By.xpath("//android.view.View[@resource-id='org.wikipedia.alpha:id/fragment_feed_header']"));
    }

    public int getSearchResults(String text){
        return waitForMinimumCountOfElements(
                By.xpath("//android.widget.TextView[contains(@text, '"+text+"')]"),2).size();

    }


    public void clearSearch() {
        doubleClick(By.id("org.wikipedia.alpha:id/search_close_btn"));
    }

    public void clickEllipsis(){
        click(By.id("org.wikipedia.alpha:id/menu_overflow_button"));
    }

    public void clickSettings(){
        click(By.xpath("//android.widget.TextView[@text='Settings']"));
    }

    public boolean toggleOff(String text){
        String locator = "//android.widget.TextView[@text='"+text+"']/../..//android.widget.Switch";
        click(By.xpath(locator));
        return getElement(By.xpath(locator)).getText().equals("OFF");
    }

    public void navigateBack(){
        click(AppiumBy.accessibilityId("Navigate up"));
    }
}
