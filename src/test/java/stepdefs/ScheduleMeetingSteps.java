package stepdefs;

import config.PlaywrightDriver;
import io.cucumber.java.en.*;

import pages.ScheduleMeetingPage;



public class ScheduleMeetingSteps {

    ScheduleMeetingPage meetingPage = new ScheduleMeetingPage();

    @Then("Schedule a Meeting button should be visible")
    public void verify_button_visible() {
        PlaywrightDriver.log("Verifying Schedule a Meeting button");
        PlaywrightDriver.verifyTrue(meetingPage.isScheduleMeetingVisible());
    }

    @When("user clicks on Schedule a Meeting")
    public void click_schedule_meeting() {
        PlaywrightDriver.log("Clicking Schedule a Meeting");
        meetingPage.clickScheduleMeetingAndSwitchTab();
    }


    @Then("booking page URL should be {string}")
    public void verify_booking_url(String expectedUrl) {

        PlaywrightDriver.log("Actual URL: " + meetingPage.getCurrentURL());
        PlaywrightDriver.log("Expected URL: " + expectedUrl);
        PlaywrightDriver.verifyText(meetingPage.getCurrentURL(), expectedUrl);

    }


    @And("user selects a date {string}")
    public void user_selects_a_date(String date) {
        meetingPage.selectDate(date);
    }



    @And("user selects a time slot {string}")
    public void user_selects_time_slot(String time) {
        meetingPage.smartSelectSlot(time);
    }


    @When("user enters first and last name {string}")
    public void enter_name(String name) {
        meetingPage.enterName(name);
    }

    @When("user enters email {string}")
    public void enter_email(String email) {
        meetingPage.enterEmail(email);
    }
    @When("user enters address {string}")
    public void enter_address(String address) {
        meetingPage.enterAddress(address);
    }

    @When("user enters phone number {string}")
    public void enter_phone(String phone) {
        meetingPage.enterPhone(phone);
    }

    @When("user enters special request {string}")
    public void enter_request(String request) {
        meetingPage.enterSpecialRequest(request);
    }

    @When("user clicks on Book button")
    public void click_book() {
        meetingPage.clickBook();
    }


    @Then("error message should be displayed for email")
    public void error_message_should_be_displayed_for_email() {
        String actual = PlaywrightDriver.getValidationMessage("booking.email");
        PlaywrightDriver.verifyText(actual, "Please fill out this field.");
    }

    @Then("error message should be displayed for name")
    public void error_message_should_be_displayed_for_name() {
        String actual = PlaywrightDriver.getValidationMessage("booking.name");
        PlaywrightDriver.verifyText(actual, "Please fill out this field.");
    }

    @Then("booking confirmation message {string} should be displayed")

    public void verify_success_msg(String expectedSuccessMsg) {
        PlaywrightDriver.log("Expected  Msg  "+expectedSuccessMsg);
        PlaywrightDriver.log("Actual  Message "+meetingPage.getMessageforBookingSuccess());
        PlaywrightDriver.verifyText(
                meetingPage.getMessageforBookingSuccess(),
                expectedSuccessMsg);
    }
    @Then("error message {string} should be displayed for selecting Time")
    public void verify_error(String expectederrorforSelectingError) {

        PlaywrightDriver.log("Expected error Msg for  selecting time"+expectederrorforSelectingError);
        PlaywrightDriver.log("Actual error Msg for selecting time"+meetingPage.getMessageforTimeErrorDisplayed());
        PlaywrightDriver.verifyText(
                meetingPage.getMessageforTimeErrorDisplayed(),
                expectederrorforSelectingError);

    }

    @Then("error message  should be displayed for Invalid Email")
    public void verify_error_for_invalid_email() {
        PlaywrightDriver.verifyTrue(
              meetingPage.isMessageforInvalidEmaill());
    }


    @And("user checks available slots and selects any available slot")
    public void user_checks_available_slots_and_selects_any_available_slot() {
        PlaywrightDriver.log("Selected slot: " +meetingPage.selectAnyAvailableSlot());
    }



}
