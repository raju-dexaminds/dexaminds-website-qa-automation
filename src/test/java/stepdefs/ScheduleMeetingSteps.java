package stepdefs;

import com.microsoft.playwright.Frame;
import config.PlaywrightDriver;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.ScheduleMeetingPage;

public class ScheduleMeetingSteps {

    ScheduleMeetingPage meetingPage = new ScheduleMeetingPage();

    @Then("Schedule a Meeting button should be visible")
    public void verify_button_visible() {
        PlaywrightDriver.log("Verifying Schedule a Meeting button");
        Assert.assertTrue(meetingPage.isScheduleMeetingVisible());
    }

    @When("user clicks on Schedule a Meeting")
    public void click_schedule_meeting() {
        PlaywrightDriver.log("Clicking Schedule a Meeting");

        meetingPage.clickScheduleMeetingAndSwitchTab();
    }


    @Then("booking page URL should be {string}")
    public void verify_booking_url(String expectedUrl) {
        String actual = meetingPage.getCurrentURL();
        PlaywrightDriver.log("Actual URL: " + actual);
        Assert.assertEquals(
                meetingPage.getCurrentURL(),
                expectedUrl,
                             "❌ Error message mismatch");

    }

    @Then("available meeting slots should be displayed")
    public void verify_slots() {
        Assert.assertTrue(meetingPage.areSlotsVisible());
    }

    @And("user selects a date {string}")
    public void user_selects_a_date(String date) {

        meetingPage.selectDate(date);
    }


    @And("user selects a time slot {string}")
    public void user_selects_time_slot(String time) {
        meetingPage.selectTimeSlot(time);
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

    @When("user confirms booking")
    public void confirm_booking() {
        PlaywrightDriver.log("Confirming booking");
        meetingPage.confirmBooking();
    }
    @Then("error message should be displayed for email")
    public void error_message_should_be_displayed_for_email() {
        String actual = PlaywrightDriver.getValidationMessage("booking.email");
        Assert.assertEquals(actual, "Please fill out this field.");
    }


    @Then("error message should be displayed for name")
    public void error_message_should_be_displayed_for_name() {
        String actual = PlaywrightDriver.getValidationMessage("booking.name");
        Assert.assertEquals(actual, "Please fill out this field.");
    }





    @Then("booking confirmation message {string} should be displayed")

    public void verify_success_msg(String expectedSuccessMsg) {
        PlaywrightDriver.log("Expected  Msg  "+expectedSuccessMsg);
        PlaywrightDriver.log("Actual  Message "+meetingPage.getMessageforBookingSuccess());
        Assert.assertEquals(
                meetingPage.getMessageforBookingSuccess(),
                expectedSuccessMsg,
                "❌ Success message mismatch"
        );

    }

    @Then("error message {string} should be displayed for selecting Time")
    public void verify_error(String expectederrorforSelectingError) {

        PlaywrightDriver.log("Expected error Msg for  selecting time"+expectederrorforSelectingError);
        PlaywrightDriver.log("Actual error Msg for selecting time"+meetingPage.getMessageforTimeErrorDisplayed());
        Assert.assertEquals(
                meetingPage.getMessageforTimeErrorDisplayed(),
                expectederrorforSelectingError,
                "❌ Error message mismatch"
        );

    }

    @Then("error message  should be displayed for Invalid Email")
    public void verify_error_for_invalid_email() {
        Assert.assertTrue(
              meetingPage.isMessageforInvalidEmaill());


    }

}
