package config;
import com.aventstack.extentreports.Status;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import utils.ExtentManager;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import static config.Hooks.extent;

public class PlaywrightDriver {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    private static ExtentTest extentTest;

    private static final Properties configProps = new Properties();
    private static final Properties locatorProps = new Properties();

    private static Throwable lastError;


    // ================= CONFIG =================

    public static void loadConfig(String filePath) {
        try (InputStream is = PlaywrightDriver.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is != null) {
                configProps.load(is);
                System.out.println("✅ Loaded config: " + filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + filePath, e);
        }
    }

    public static void loadLocators(String filePath) {
        try (InputStream is = PlaywrightDriver.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is != null) {
                locatorProps.load(is);
                System.out.println("✅ Loaded locators: " + filePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators: " + filePath, e);
        }
    }

    public static String getConfig(String key) {
        String value = configProps.getProperty(key);
        if (value == null) throw new RuntimeException("❌ Config key not found: " + key);
        return value;
    }

    public static String getLocator(String key) {
        String value = locatorProps.getProperty(key);
        if (value == null) throw new RuntimeException("❌ Locator key not found: " + key);
        return value;
    }

    // ================= INIT =================

    // ================= INIT DRIVER =================
    public static void initDriver() {

        String browserName = getConfig("browser");
        boolean headless = Boolean.parseBoolean(getConfig("headless"));

        playwright = Playwright.create();

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions().setHeadless(headless);

        switch (browserName.toLowerCase()) {

            case "firefox":
                browser = playwright.firefox().launch(
                        new BrowserType.LaunchOptions().setHeadless(headless)
                );
                context = browser.newContext(
                        new Browser.NewContextOptions().setViewportSize(null)
                );
                break;

            case "webkit":
                browser = playwright.webkit().launch(
                        new BrowserType.LaunchOptions().setHeadless(headless)
                );
                context = browser.newContext(
                        new Browser.NewContextOptions().setViewportSize(null)
                );
                break;

            default: // ✅ CHROMIUM (FULLSCREEN WORKS HERE)
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(headless)
                                .setArgs(List.of("--start-maximized"))
                );

                context = browser.newContext(
                        new Browser.NewContextOptions()
                                .setViewportSize(null)
                );
        }

        page = context.newPage();
    }

    public static Page getPage() {
        if (page == null) throw new IllegalStateException("Playwright not initialized");
        return page;
    }

    // ================= BASIC ACTIONS =================

    public static void navigateTo(String url) {
        getPage().navigate(url);
        waitForTime(2000);
    }

    public static void click(String locatorKey) {
        Locator loc = getPage().locator(getLocator(locatorKey));
       loc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        loc.click();
        waitForTime(2000);
    }
//    public static void click(String locatorKey) {
//        log("🖱 Clicking: " + locatorKey);
//        page.locator(getLocator(locatorKey)).click();
//        sleep(2000);
//    }
    /* ---------- CLICKABLE CHECK ---------- */
    public static boolean isClickable(String locator) {
        try {
            Locator element = getPage().locator(locator);

            element.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));

            // Anchor must have href
            String href = element.getAttribute("href");
            return href != null && !href.isEmpty();

        } catch (Exception e) {
            return false;
        }
    }

//    public static void clickDynamic(String xpath) {
//        Locator loc = getPage().locator(xpath);
//        loc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
//        loc.click();
//        waitForTime(1000);
//    }

    public static void type(String locatorKey, String value) {
        Locator loc = getPage().locator(getLocator(locatorKey));
        loc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        loc.fill(value);
    }

    public static String getText(String locatorKey) {
        Locator loc = getPage().locator(getLocator(locatorKey));
        loc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return loc.textContent().trim();
    }


    public static boolean isVisible(String locatorKey) {


        Locator element = getPage().locator(getLocator(locatorKey));

        // 2️⃣ Ensure element exists in DOM
        element.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED));

        // 3️⃣ Scroll into view (important for long pages)
        element.scrollIntoViewIfNeeded();

        // 4️⃣ Wait until visible
        element.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
     PlaywrightDriver.waitForTime(10000);
        return element.isVisible();
    }

    public static void scrollTo(String locatorKey) {
        Locator loc = getPage().locator(getLocator(locatorKey));
        loc.scrollIntoViewIfNeeded();
    }

//    public static void scrollAndClick(String locatorKey) {
//        Locator loc = getPage().locator(getLocator(locatorKey));
//        loc.scrollIntoViewIfNeeded();
//        loc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
//        loc.first().click();
//    }

    public static void waitForTime(int millis) {
        getPage().waitForTimeout(millis);
    }

    // ================= NEW TAB =================

    public static void switchToNewTabAfterClick(String locatorKey) {
        Page newPage = getPage().context().waitForPage(() -> {
            click(locatorKey);
        });
        page = newPage;
        page.waitForLoadState();
        log("🆕 Switched to new tab: " + page.url());
    }

    // ================= CALENDAR =================

    public static void selectDateFromCalendar(String date) {
        try {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            String monthName = getMonthName(month);
            String expectedHeader = monthName + " " + year;

            Locator header = getPage().locator(getLocator("booking.calendar.MonthYear"));
            Locator nextBtn = getPage().locator(getLocator("booking.NextBtn"));

            for (int i = 0; i < 12; i++) {
                String current = header.innerText().trim();
                if (current.equalsIgnoreCase(expectedHeader)) break;
                nextBtn.click();
                waitForTime(500);
            }

            String dayXpath = String.format(getLocator("booking.dateXpath"), day);
            clickDynamic(dayXpath);

            log("✅ Date selected: " + date);

        } catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }

    // ================= TIME SLOT =================

    public static void TimeSlot(String time) {
        try {
            log("⏰ Selecting time slot: " + time);

            String xpath = String.format(getLocator("booking.timeXpath"), time);
            clickDynamic(xpath);

            log("✅ Time slot selected: " + time);
        } catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }
    public static void clickDynamic(String xpath) {
        try {
            Locator loc = getPage().locator(xpath).first();

            // Wait until element exists in DOM
            loc.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED));

            // Scroll into view
            loc.scrollIntoViewIfNeeded();

            // Wait until visible
            loc.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));

            loc.click();
            waitForTime(1000);
        }
        catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }



    public static void clickByXpath(String xpath) {
        Locator loc = getPage().locator(xpath);
        loc.scrollIntoViewIfNeeded();
        loc.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        loc.click();
    }

    // ================= FILE UPLOAD =================

    public static void uploadResume(String locatorKey, String fileName) {

        Path filePath = Paths.get("src/test/resources/testdata/" + fileName);

        if (!Files.exists(filePath)) {
            RuntimeException ex = new RuntimeException("❌ Resume file not found: " + filePath);
            setLastError(ex);
            throw ex;

        }

        getPage().setInputFiles(getLocator(locatorKey), filePath);
    }



    // ================= LOGGING =================

    public static void setExtentTest(ExtentTest test) {
        extentTest = test;
    }

    public static void log(String message) {
        System.out.println(message);
        if (extentTest != null) extentTest.info(message);
    }

    // ================= CLEANUP =================

    public static void tearDown() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
            browser = null;
            context = null;
            page = null;
        }
    }

    // ================= UTIL =================

    private static String getMonthName(int month) {
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> throw new IllegalArgumentException("Invalid month: " + month);
        };
    }

    public static String getTitle() {
        return getPage().title();
    }


        public static String getDynamicLocator(String key, String value) {
            String locatorTemplate = locatorProps.getProperty(key);

            if (locatorTemplate == null) {
                throw new RuntimeException("❌ Dynamic locator key NOT FOUND: " + key);
            }

            return String.format(locatorTemplate, value);
        }
    public static String getValidationMessage(String locatorKey) {
        try {
            String selector = getLocator(locatorKey);

            String message = getPage().evaluate(
                    "el => el.validationMessage",
                    getPage().querySelector(selector)
            ).toString();

            return message;

        } catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }




    public static String takeScreenshot(String name) {
        try {
            if (page == null) return null;

            String path = ExtentManager.reportDir + "/screenshots/" + name + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
            return "screenshots/" + name + ".png"; // relative path
        } catch (Exception e) {
            return null;
        }
    }




    public static void logFailure(Throwable error) {
        try {
            if (error == null) {
                extentTest.fail("❌ Scenario failed (no exception captured)");
                return;
            }

            extentTest.fail("❌ Failure Reason: " + error.getMessage());

            // Full stacktrace
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement el : error.getStackTrace()) {
                sb.append(el.toString()).append("<br>");
            }

            extentTest.fail(sb.toString());

            // Screenshot
            String screenshotPath = takeScreenshot("Failure_" + System.currentTimeMillis());
            if (screenshotPath != null) {
                extentTest.addScreenCaptureFromPath(screenshotPath);
            }

        } catch (Exception e) {
            setLastError(e);
            throw e;
        }
    }




    public static void setLastError(Throwable e) {
        lastError = e;
    }

    public static Throwable getLastError() {
        return lastError;
    }

}

