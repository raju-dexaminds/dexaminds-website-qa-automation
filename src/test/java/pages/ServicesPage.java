package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import config.PlaywrightDriver;
import com.microsoft.playwright.Locator;
import java.util.ArrayList;
import java.util.List;
public class ServicesPage {
    public void selectService(String service) {
        PlaywrightDriver.log("Selecting Service is " + service);
        String xpath = String.format(
                PlaywrightDriver.getLocator("services.option.dynamic"),
                service
        );
        PlaywrightDriver.clickByXpath(xpath);
        PlaywrightDriver.waitForTime(3000);
    }
    public List<String> getAllServiceOptions() {
        PlaywrightDriver.log("Getting All Services  ");
        List<String> ServiceOptions = new ArrayList<>();

        Locator services = PlaywrightDriver.getPage()
                .locator(PlaywrightDriver.getLocator("services.options"));

        int count = services.count();

        for (int i = 0; i < count; i++) {
            ServiceOptions.add(services.nth(i).innerText().trim());
            PlaywrightDriver.waitForTime(2000);
        }

        return ServiceOptions;

    }
    public void hoverOnServicesDropdown() {
        PlaywrightDriver.log("Hovering on  Services Dropdown ");
        PlaywrightDriver.waitForTime(5000);
        Page page = PlaywrightDriver.getPage();
        String locator = PlaywrightDriver.getLocator("services.dropdown");

        page.waitForSelector(locator,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

        page.hover(locator);


    }
    public String getPageHeadingTitle() {
        PlaywrightDriver.log("Getting Page Heading Title");

        String[] headingKeys = {
                "services.allServicesHeading",
                "services.AiEngineeringHeading",
                "services.softwareDevelopmentHeading",
                "services.QualityAssuranceHeading",
                "services.DevOpsHeading",
                "services.CloudSolutionsHeading",
                "services.DigitalTransformationHeading"
        };

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
