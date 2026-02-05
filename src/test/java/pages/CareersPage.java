package pages;

import com.microsoft.playwright.Locator;
import config.PlaywrightDriver;

import java.util.ArrayList;
import java.util.List;

public class CareersPage {

    public static List<String> getAvailableJobPositions() {
        List<String> jobTitles = new ArrayList<>();

        Locator jobs = PlaywrightDriver.getPage()
                .locator(PlaywrightDriver.getLocator("career.jobTitles"));

        int count = jobs.count();

        for (int i = 0; i < count; i++) {
            jobTitles.add(jobs.nth(i).innerText().trim());
            PlaywrightDriver.waitForTime(2000);
        }

        return jobTitles;
    }

    public String getNoJobsMessage() {

        Locator jobs = PlaywrightDriver.getPage()
                .locator(PlaywrightDriver.getLocator("career.jobTitles"));

        if (jobs.count() == 0) {
            String message = "No job positions are available";
            PlaywrightDriver.log(message);
            return message;
        }

        return null; // or return empty string ""
    }

    public void clickViewPositions() {
        PlaywrightDriver.click("careers.viewOpenPositions");
        PlaywrightDriver.log("User clicks on View Open Positions button");
    }

    public boolean isOpenPositionsVisible() {
        return PlaywrightDriver.isVisible("careers.openPositionsSection");
    }


    public boolean isViewPositionsButtonVisible() {
        return PlaywrightDriver.isVisible("careers.viewOpenPositions");

    }

    public boolean areJobPositionsDisplayed() {
        return PlaywrightDriver.isVisible("career.jobSection");
    }

    public String getResumeSizeErrorMessage() {
        return PlaywrightDriver.getText(("careers.resume.size.error"));
    }

    public String getCareersPageURL() {
        return PlaywrightDriver.getPage().url();
    }

    public void clickCareersUs() {
        PlaywrightDriver.click("careers.link");
        PlaywrightDriver.log("User clicks on Careers link");

    }

    public void clickViewDetailsForJob(String jobTitle) {
        String dynamicXpath = PlaywrightDriver.getDynamicLocator(
                "career.viewDetails.byJob",
                jobTitle
        );

        PlaywrightDriver.clickByXpath(dynamicXpath);
        PlaywrightDriver.waitForTime(5000);
        PlaywrightDriver.scrollTo("job.apply.button");
        PlaywrightDriver.log("User clicks on View Details for job: " + jobTitle);
    }


}



