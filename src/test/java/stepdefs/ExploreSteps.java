package stepdefs;

import config.PlaywrightDriver;
import io.cucumber.java.en.*;
import pages.ExplorePage;

public class ExploreSteps {

    ExplorePage explorePage = new ExplorePage();



    @When("user hovers on explore dropdown")
    public void user_hovers_on_explore_dropdown() {
        explorePage.hoverOnExploreDropdown();
    }

    @Then("all explore options should be visible")
    public void all_explore_options_should_be_visible() {
        PlaywrightDriver.log("Available services: are" + explorePage.getAllExploreOptions() );

    }

    @When("user selects a {string}")
    public void user_selects(String exploreType) {
        explorePage.selectExploreOption(exploreType);
    }

    @Then("verify heading should be {string}")
    public void verify_heading_is(String expectedHeading) {
        PlaywrightDriver.log("Expected page heading_is "+expectedHeading);
        PlaywrightDriver.log("Actual page heading_is "+explorePage.getHeadingText());

        PlaywrightDriver.verifyText(explorePage.getHeadingText(), expectedHeading);

    }
}
