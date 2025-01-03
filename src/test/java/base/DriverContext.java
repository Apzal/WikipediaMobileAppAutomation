package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class DriverContext {

    AppiumDriverLocalService service;
    public AndroidDriver driver;
    public PageInstance currentPage;
}
