package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(features = {"src/test/resources/features"},
                glue = {"steps","hooks"},
                tags = "@mobile",
                plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "pretty"})
public class MobileRunCucumberTest extends AbstractTestNGCucumberTests {
}
