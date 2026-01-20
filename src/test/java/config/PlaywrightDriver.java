package config;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;


import java.io.File;
import java.io.FileInputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Properties;



public class PlaywrightDriver {


    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;


    private static ExtentTest extentTest;

    private static final Properties configProps = new Properties();
    private static final Properties locatorProps = new Properties();


    private static Throwable lastError;

    public static void setLastError(Throwable error) {
        lastError = error;
    }

    protected static Throwable getLastError() {
        return lastError;
    }

    // ================= CONFIG =================





    public static String getConfig(String key) {
        String value = configProps.getProperty(key);
        if (value == null) throw new RuntimeException("❌ Config key not found: " + key);
        return value;
    }

    public static String getLocator(String key) {
        String value = locatorProps.getProperty(key);
        if (value == null) {
            throw new RuntimeException("❌ Locator key not found: " + key +
                    "\nAvailable keys: " + locatorProps.keySet());
        }
        return value.trim();
    }

    private static void loadProperties() {
        try {
            File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/Locators");

            if (!directoryPath.exists()) {
                throw new RuntimeException("❌ Locators directory NOT found: " + directoryPath.getAbsolutePath());
            }

            File[] filesList = directoryPath.listFiles((dir, name) -> name.endsWith(".properties"));

            if (filesList == null || filesList.length == 0) {
                throw new RuntimeException("❌ No locator property files found in: " + directoryPath.getAbsolutePath());
            }

            for (File file : filesList) {
                FileInputStream fis = new FileInputStream(file);
                locatorProps.load(fis);
                fis.close();
                System.out.println("✅ Loaded locator file: " + file.getName());
            }

            System.out.println("✅ All locator properties loaded automatically");

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load locator properties", e);
        }
    }
    private static void loadAllConfigs() {
        try {
            File directoryPath = new File(System.getProperty("user.dir") + "/src/test/resources/config");

            if (!directoryPath.exists()) {
                throw new RuntimeException("❌ Config directory NOT found: " + directoryPath.getAbsolutePath());
            }

            File[] filesList = directoryPath.listFiles((dir, name) -> name.endsWith(".properties"));

            if (filesList == null || filesList.length == 0) {
                throw new RuntimeException("❌ No config property files found in: " + directoryPath.getAbsolutePath());
            }

            for (File file : filesList) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    configProps.load(fis);
                    System.out.println("✅ Loaded config: " + file.getName());
                }
            }

            System.out.println("✅ All config files loaded automatically");

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load config files", e);
        }
    }



    // ================= INIT =================

    // ================= INIT DRIVER =================
    public static void initDriver() {
        // ✅ Load config files first
        loadAllConfigs();

        // ✅ Load locator properties
        loadProperties();

        String browserName = getConfig("browser");
        boolean headless = Boolean.parseBoolean(getConfig("headless"));

        playwright = Playwright.create();



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



    public static void waitForTime(int millis) {
        getPage().waitForTimeout(millis);
    }

    // ================= NEW TAB =================

    public static void switchToNewTabAfterClick(String locatorKey) {
        Page newPage = getPage().context().waitForPage(() -> {
            click(locatorKey);
        });

        newPage.waitForLoadState();

        // 🔥 IMPORTANT: update global page reference
        setPage(newPage);

        log("🆕 Switched to new tab: " + newPage.url());
    }

    private static void setPage(Page newPage) {
        page = newPage;
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
// Move to correct month/year
            for (int i = 0; i < 12; i++) {
                String current = header.innerText().trim();
                if (current.equalsIgnoreCase(expectedHeader)) break;
                nextBtn.click();
                waitForTime(500);
            }

            String dayXpath = String.format(getLocator("booking.dateXpath"), day);
            waitForTime(500);
            clickByXpath(dayXpath);

            log("✅ Date selected: " + date);

        } catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }









    public static void clickByXpath(String xpath) {
        Locator loc = getPage().locator(xpath);
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

            return getPage().evaluate(
                    "el => el.validationMessage",
                    getPage().querySelector(selector)
            ).toString();

        } catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }





    protected static void setExtentTest(ExtentTest test) {
        extentTest=test;
    }

    public static void verifyText(String actual, String expected) {
        try {
            if (!actual.equals(expected)) {
                String msg = "❌ Text verification failed. Expected: [" + expected + "] but found: [" + actual + "]";
                log(msg);
                throw new RuntimeException(msg);
            } else {
                log("✅ Text verified successfully: " + actual);
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Text verification error: " + e.getMessage());
        }
    }
    public static void verifyTrue(boolean condition) {
        if (!condition) {
            throw new RuntimeException("❌ Condition is FALSE");
        } else {
            log("✅ Condition satisfied");
        }
    }


}

