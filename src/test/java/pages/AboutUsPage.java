package pages;

import config.PlaywrightDriver;

public class AboutUsPage {

    public void clickAboutUs() {
        PlaywrightDriver.click("about.link");
    }

    public boolean isHeadingVisible() {
        return PlaywrightDriver.isVisible(
                PlaywrightDriver.getLocator("about.heading"));
    }

    public boolean isDescriptionVisible() {
        return PlaywrightDriver.isVisible(
                PlaywrightDriver.getLocator("about.description"));
    }

    public boolean isMissionVisible() {
        return PlaywrightDriver.isVisible(
                PlaywrightDriver.getLocator("about.mission"));
    }

    public boolean hasImages() {
        return PlaywrightDriver.getPage()
                .locator(PlaywrightDriver.getLocator("about.images"))
                .count() > 0;
    }

    public boolean isPageBlank() {
        return PlaywrightDriver.getPage().content().length() < 200;
    }
    // ================= NAVIGATION =================

    public void clickAboutUsLink() {
        PlaywrightDriver.click("aboutus.link");
    }

    public boolean isAboutUsPageOpened() {
        return PlaywrightDriver.getPage().url().contains("/about");
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

    public boolean isHomePageDisplayed() {
        return !PlaywrightDriver.getPage().url().contains("/about");
    }

    public String getHomePageTitle() {
        return PlaywrightDriver.getTitle();
    }


    // ================= NEGATIVE VALIDATIONS =================



//    public boolean hasEmptyContentBlocks() {
//        return PlaywrightDriver.getPage()
//                .locator(PlaywrightDriver.getLocator("aboutus.empty.blocks"))
//                .count() > 0;
//    }
//
//    public boolean isErrorMessageDisplayed() {
//        return PlaywrightDriver.isVisible("aboutus.error.message");
//    }


}
