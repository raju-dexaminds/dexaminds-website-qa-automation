package stepdefs;

import config.PlaywrightDriver;
import io.cucumber.java.en.*;

import pages.ServicesPage;



public class ServicesSteps {

    ServicesPage servicesPage = new ServicesPage();


    @When("user hovers on Services dropdown")
    public void userHoversOnServicesDropdown() {

        servicesPage.hoverOnServicesDropdown();
    }

    @Then("get all services options")
    public void allServiceOptionsShouldBeVisible() {
        PlaywrightDriver.log("Available services: are" + servicesPage.getAllServiceOptions() );

    }





    @When("user selects {string}")
    public void user_selects(String service) {
        servicesPage.selectService(service);
    }

    @Then("verify heading is {string}")
    public void verify_heading_is(String expected) {
        PlaywrightDriver.log("Expected page heading_is "+expected);
        PlaywrightDriver.log("Actual page heading_is "+servicesPage.getPageHeadingTitle());

        PlaywrightDriver.verifyText(servicesPage.getPageHeadingTitle(), expected);
    }

}
