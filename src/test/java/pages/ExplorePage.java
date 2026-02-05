package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import config.PlaywrightDriver;

import java.util.ArrayList;
import java.util.List;

public class ExplorePage {

    public void hoverOnExploreDropdown() {
        PlaywrightDriver.log("Hovering on Explore Dropdown ");
        PlaywrightDriver.waitForTime(5000);
        Page page = PlaywrightDriver.getPage();
        String locator = PlaywrightDriver.getLocator("explore.dropdown");
        page.waitForSelector(locator, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        page.hover(locator);
    }


    public void selectExploreOption(String exploreType) {
        PlaywrightDriver.log("Selecting ExploreType is " + exploreType);
        String xpath = String.format(
                PlaywrightDriver.getLocator("explore.option.dynamic"),
                exploreType
        );
        PlaywrightDriver.clickByXpath(xpath);
        PlaywrightDriver.waitForTime(3000);
    }

    public List<String> getAllExploreOptions() {
        PlaywrightDriver.log("Getting All Explore options  ");
        List<String> ExploreOptions = new ArrayList<>();

        Locator exploreOptions = PlaywrightDriver.getPage()
                .locator(PlaywrightDriver.getLocator("explore.options"));

        int count = exploreOptions.count();

        for (int i = 0; i < count; i++) {
            ExploreOptions.add(exploreOptions.nth(i).innerText().trim());
            PlaywrightDriver.waitForTime(2000);
        }

        return ExploreOptions;
    }

    public String getHeadingText() {
        PlaywrightDriver.log("Getting Page Heading Title");

        String[] headingKeys = {
                "explore.ApisHeading",
                "explore.BlogsHeading"};


        for (String key : headingKeys) {
            try {
                Locator loc = PlaywrightDriver.getPage()
                        .locator(PlaywrightDriver.getLocator(key));

                if (loc.count() > 0 && loc.first().isVisible()) {
                    return PlaywrightDriver.getText(key);
                }
            } catch (Exception e) {
                // Ignore and continue checking next heading
            }
        }

        throw new RuntimeException("❌ No matching page heading found!");
    }
}

