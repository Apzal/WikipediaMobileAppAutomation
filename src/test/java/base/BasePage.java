package base;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ReadPropertyFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;

public class BasePage extends PageInstance {
    private final ReadPropertyFile readProperty;
    private final DriverContext driverContext;
    private int defaultTimeOutInSeconds = 0;
    String deviceName = "";
    String appPath = "";
    String appPackage = "";
    String appActivity = "";

    Logger logger = LogManager.getLogger(BasePage.class);
    public BasePage(DriverContext driverContext) {
        super(driverContext);
        this.driverContext = driverContext;
        readProperty = new ReadPropertyFile();
        setConfigurations();
    }

    private void setConfigurations() {
        appPath = System.getProperty("user.dir") + "\\src\\test\\resources\\apks\\"+readProperty.readProperty("apk");
        deviceName = System.getProperty("deviceName");
        appPackage = System.getProperty("appPackage");
        appActivity = System.getProperty("appActivity");
        defaultTimeOutInSeconds = Integer.parseInt(readProperty.readProperty("defaultTimeOutInSeconds"));
    }

    public void launchApp() throws URISyntaxException, MalformedURLException {
        this.driverContext.service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1").usingPort(4723).build();
        this.driverContext.service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setApp(appPath);
        options.setAppPackage(appPackage);
        options.setAppActivity(appActivity);
        options.setPlatformName("Android");
        options.setAutomationName("UIAutomator2");
        this.driverContext.driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723/").toURL(), options);
    }

    private WebDriverWait loadWaitTimer(int timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(this.driverContext.driver, Duration.ofSeconds(timeOutInSeconds));
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementClickInterceptedException.class);
        wait.ignoring(ElementNotInteractableException.class);
        return wait;
    }

    public WebElement getElement(By locator, int timeOutInSeconds){
        WebDriverWait wait = loadWaitTimer(timeOutInSeconds);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("Located Element, Locator Used : {}", element);
        return element;
    }

    public WebElement getElement(By locator){
        WebDriverWait wait = loadWaitTimer(defaultTimeOutInSeconds);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.info("Located Element, Locator Used : {}", element);
        return element;
    }


    public void click(By locator){
        int counter = 3;
        while (counter > 0)
            try {
                getElement(locator, defaultTimeOutInSeconds).click();
                logger.info("Clicked Element, Locator Used : {}", locator);
                break;
            } catch (WebDriverException e) {
                counter--;
                if (counter == 0) {
                    logger.info("Click element failed, Locator Used : {},Exception :{}", locator, e.getMessage());
                    throw new WebDriverException(e.getMessage());
                }
                else{
                    logger.info("Click element failed,Retrying click, Attempt left:{}, Locator Used : {},Exception :{}", counter, locator, e.getMessage());
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }

    }

    public void scrollDown(){
        this.driverContext.driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollToEnd(1, 10)"));
    }

    public void pause(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void scrollTopTillElement(String resourceId){
        this.driverContext.driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"))"));
    }

    public void setText(By locator, String text){
        getElement(locator).sendKeys(text);
    }

    public List<WebElement> waitForMinimumCountOfElements(By locator,int count) {
        WebDriverWait wait = loadWaitTimer(defaultTimeOutInSeconds);
        logger.info("Retrieve Multiple Elements, Locator Used : {}", locator);
        return wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator,count));
    }

    public void doubleClick(By locator){
        WebElement element = getElement(locator);
        ((JavascriptExecutor) this.driverContext.driver)
                .executeScript("mobile: doubleClickGesture", ImmutableMap.of("elementId", element));
    }

}
