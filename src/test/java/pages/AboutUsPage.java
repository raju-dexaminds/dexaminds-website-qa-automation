package pages;

import config.PlaywrightDriver;

public class AboutUsPage {

    public void clickAboutUs() {
        PlaywrightDriver.click("about.link");
    }

    // ================= TITLE & URL =================

    public String getAboutUsPageTitle() {
        return PlaywrightDriver.getTitle();
    }

    public String getAboutUsPageURL() {
        return PlaywrightDriver.getPage().url();
    }

    // ================= UI VALIDATIONS =================

    public boolean isMainHeadingVisible() {
        return PlaywrightDriver.isVisible("about.heading");
    }

    public boolean isCompanyDescriptionVisible() {
        return PlaywrightDriver.isVisible("about.description");
    }

    public boolean isMissionVisionVisible() {
        return PlaywrightDriver.isVisible("about.mission");
    }


    // ================= NAVIGATION BACK =================

    public void clickHomeLink() {
        PlaywrightDriver.click("about.home.link");
    }


    public String getHomePageTitle() {
        return PlaywrightDriver.getTitle();
    }


}
