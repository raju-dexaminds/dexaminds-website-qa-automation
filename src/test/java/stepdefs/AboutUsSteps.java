package stepdefs;


import config.PlaywrightDriver;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.AboutUsPage;

public class AboutUsSteps {

    AboutUsPage aboutUsPage = new AboutUsPage();

    @And("user clicks on About Us link")
    public void clickAbout() {
        aboutUsPage.clickAboutUs();
    }



    @Then("verify mission or vision section is visible")
    public void verifyMission() {
        Assert.assertTrue(aboutUsPage.isMissionVisible());
    }

    @Then("verify all About Us images are displayed properly")
    public void verifyImages() {
        Assert.assertTrue(aboutUsPage.hasImages());
    }

    @Then("verify About Us page is not blank")
    public void verifyNotBlank() {
        Assert.assertFalse(aboutUsPage.isPageBlank());
    }





    @Then("verify About Us page title is correct")
    public void verify_about_us_page_title_is_correct() {
        String actualTitle = aboutUsPage.getAboutUsPageTitle();
    String  expectedTitle=PlaywrightDriver.getConfig("about.ExpectedTitle");

        PlaywrightDriver.log("About Us page Actual Title: " + actualTitle);
       PlaywrightDriver.log("About Us page Expected Title: " + expectedTitle);

        Assert.assertEquals(actualTitle, expectedTitle,
                "❌ About Us page title mismatch");
    }

    @Then("verify About Us page URL is correct")
    public void verify_about_us_page_url_is_correct() {
        String actualUrl = aboutUsPage.getAboutUsPageURL();
String expectedUrl=PlaywrightDriver.getConfig("about.ExpectedUrl");

        PlaywrightDriver.log("About Us page Actual Url: " + actualUrl);
        PlaywrightDriver.log("About Us page Expected Url: " + expectedUrl);
        Assert.assertTrue(actualUrl.contains(expectedUrl),
                "❌ About Us URL incorrect");
    }

    @Then("verify About Us main heading is visible")
    public void verify_about_us_main_heading_is_visible() {
        Assert.assertTrue(
                aboutUsPage.isMainHeadingVisible(),
                "❌ About Us main heading not visible"
        );
    }

    @Then("verify company description content is displayed")
    public void verify_company_description_content_is_displayed() {
        Assert.assertTrue(
                aboutUsPage.isCompanyDescriptionVisible(),
                "❌ Company description not visible"
        );
    }

    @Then("verify mission and vision section are visible")
    public void verify_mission_or_vision_section_is_visible() {
        Assert.assertTrue(
                aboutUsPage.isMissionVisionVisible(),
                "❌ Mission/Vision section not visible"
        );
    }



    @When("user clicks on Home link")
    public void user_clicks_on_home_link() {
        aboutUsPage.clickHomeLink();
    }

    @Then("verify Home page is displayed")
    public void verify_home_page_is_displayed() {
        String actualTitle = aboutUsPage.getHomePageTitle();
        String  expectedTitle=PlaywrightDriver.getConfig("home.ExpectedTitle");

        PlaywrightDriver.log("Home page Actual Title: " + actualTitle);
        PlaywrightDriver.log("Home Page Expected Title: " + expectedTitle);

        Assert.assertEquals(actualTitle, expectedTitle,
                "❌ Home Page title mismatch");
    }







    }
