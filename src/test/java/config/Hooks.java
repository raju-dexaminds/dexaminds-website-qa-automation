package config;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.Page;
import io.cucumber.java.*;
import utils.ExtentManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    static ExtentReports extent;
    private static ExtentTest test;

    @Before
    public void beforeScenario(Scenario scenario) {


        // Init browser
        PlaywrightDriver.initDriver();

        // Navigate to base URL
        PlaywrightDriver.navigateTo(
                PlaywrightDriver.getConfig("loginUrl")
        );

        // Init Extent
        extent = ExtentManager.getExtent();
        test = extent.createTest(scenario.getName());

        // Attach Extent test to Driver
        PlaywrightDriver.setExtentTest(test);

        // Logs
        PlaywrightDriver.log("🚀 Scenario Started: " + scenario.getName());
    }
    @AfterStep
    public void afterStep(Scenario scenario) throws IOException {
        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            Path screenshotDir = Path.of(ExtentManager.reportDir, "screenshots");
            Files.createDirectories(screenshotDir);

            String fileName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
                    + "_" + timestamp + ".png";

            Path screenshotPath = screenshotDir.resolve(fileName);

            PlaywrightDriver.getPage().screenshot(
                    new Page.ScreenshotOptions().setPath(screenshotPath)
            );

            String relativePath = "screenshots/" + fileName;

            if (scenario.isFailed()) {
                test.fail("❌ Step Failed")
                        .addScreenCaptureFromPath(relativePath);
            } else {
                test.pass("✅ Step Passed")
                        .addScreenCaptureFromPath(relativePath);
            }

        }  catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            Throwable error = PlaywrightDriver.getLastError();

            if (error != null) {
                test.fail("❌ " + error.getMessage());
                test.fail(error);
            } else {
                test.fail("❌ Scenario failed (no exception captured)");
            }
        } else {
            test.pass("✅ Scenario Passed");
        }

        extent.flush();
        PlaywrightDriver.tearDown();
    }

}
