package stepdefs;

import config.PlaywrightDriver;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.ContactUsPage;

public class ContactUSSteps {

    ContactUsPage contactUsPage = new ContactUsPage();



    @Given("user navigates to homePage_url")
    public void navigateHome() {
        PlaywrightDriver.navigateTo(PlaywrightDriver.getConfig("loginUrl"));
    }


    @When("user clicks on Contact US Button in home page")
    public void clickContact() {
        contactUsPage.clickContactUs();
    }


    @When("user enters valid Name")
    public void enterName() {
        contactUsPage.enterName(PlaywrightDriver.getConfig("name"));
    }

    @When("user enters valid Email")
    public void enterEmail() {
        contactUsPage.enterEmail(PlaywrightDriver.getConfig("email"));
    }

    @When("user enters valid Message")
    public void enterMessage() {
        contactUsPage.enterMessage(PlaywrightDriver.getConfig("message"));
    }

    @When("user clicks Send Message button")
    public void user_clicks_Send_Message_button() {
        contactUsPage.clickSend();
    }


    @Then("verify success message {string} is displayed")
    public void verify_success_message_is_displayed(String expectedSuccessMsg) {
        PlaywrightDriver.log("Expected  Msg  "+expectedSuccessMsg);
        PlaywrightDriver.log("Actual  Message "+contactUsPage.getSuccessMessageDisplayed());
        Assert.assertEquals(
                contactUsPage.getSuccessMessageDisplayed(),
                expectedSuccessMsg,
                "❌ Success message mismatch"
        );

       }


    @Then("verify Contact Us page loads successfully")
    public void verify_contact_us_page_loads_successfully() {
        Assert.assertTrue(
                contactUsPage.isContactUsPageOpened(),
                "❌ Contact Us page did not load successfully"
        );
    }

    @Then("verify Name field is visible and enabled")
    public void verify_name_field_is_visible_and_enabled() {
        Assert.assertTrue(
                contactUsPage.isNameFieldVisibleAndEnabled(),
                "❌ Name field is not visible or enabled"
        );
    }

    @Then("verify Email field is visible and enabled")
    public void verify_email_field_is_visible_and_enabled() {
        Assert.assertTrue(
                contactUsPage.isEmailFieldVisibleAndEnabled(),
                "❌ Email field is not visible or enabled"
        );
    }

    @Then("verify Message field is visible and enabled")
    public void verify_message_field_is_visible_and_enabled() {
        Assert.assertTrue(
                contactUsPage.isMessageFieldVisibleAndEnabled(),
                "❌ Message field is not visible or enabled"
        );
    }

    @Then("verify Send Message button is visible and enabled")
    public void verify_send_message_button_is_visible_and_enabled() {
        Assert.assertTrue(
                contactUsPage.isSendMessageButtonVisibleAndEnabled(),
                "❌ Send Message button is not visible or enabled"
        );
    }
    @Then("error message {string} should be displayed for invalid Name")
    public void error_message_should_be_displayed_for_invalid_name(String expectederrorMsgforinvalidName) {
     System.out.println("Expected error Msg forinvalidName "+expectederrorMsgforinvalidName);
        System.out.println("Actual error Msg forinvalidName "+contactUsPage.getErrorMessageInvalidatorName());
        Assert.assertEquals(
                contactUsPage.getErrorMessageInvalidatorName(),
                expectederrorMsgforinvalidName,
                "❌ Error message mismatch"
        );
    }





    @When("user enters email as {string}")
    public void user_enters_email_as(String email) {
        contactUsPage.enterEmail(email);
    }

    @When("user enters name as {string}")
    public void user_enters_name_as(String name) {
        contactUsPage.enterName(name);
    }


    @When("user enters message as {string}")
    public void user_enters_message_as(String message) {
        contactUsPage.enterMessage(message);
    }

    @Then("error message {string} should be displayed for Name length validation")
    public void error_message_should_be_displayed_for_name_length_validation(String expectederrorforNameLengthValidation ) {
        System.out.println("Expected error Msg Name length validation "+expectederrorforNameLengthValidation);
        System.out.println("Actual error Msg Name length validation "+contactUsPage.getNameLengthErrorDisplayed());
        Assert.assertEquals(
                contactUsPage.getNameLengthErrorDisplayed(),
                expectederrorforNameLengthValidation,
                "❌ Error message mismatch"
        );
    }


    @Then("error message {string} should be displayed for invalid Message")
    public void error_message_should_be_displayed_for_invalid_message(String expectederrorMsgforinvalidMsg) {
        System.out.println("Expected error Msg for invalid Message "+expectederrorMsgforinvalidMsg);
        System.out.println("Actual error Msg for invalid Message "+contactUsPage.getErrorMessageInvalidatorMessage());
        Assert.assertEquals(
                contactUsPage.getErrorMessageInvalidatorMessage(),
                expectederrorMsgforinvalidMsg,
                "❌ Error message mismatch"
        );

    }

    @Then("error message {string} should be displayed for Invalid email format")
    public void error_message_should_be_displayed_for_invalid_email_format(String expectederrorMsgforinvalidEmail) {
        System.out.println("Expected error Msg for invalid Message "+expectederrorMsgforinvalidEmail);
        System.out.println("Actual error Msg for invalid Message "+contactUsPage.getErrorMessageInvalidatorEmail());
        Assert.assertEquals(
                contactUsPage.getErrorMessageInvalidatorEmail(),
                expectederrorMsgforinvalidEmail,
                "❌ Error message mismatch"
        );
    }

    @Then("error message {string} should be displayed for Message length validation")
    public void error_message_should_be_displayed_for_message_validation(String expectederrorforMsgLengthValidation) {

        System.out.println("Expected error Msg for  Message length validation "+expectederrorforMsgLengthValidation);
        System.out.println("Actual error Msg for Message length validation "+contactUsPage.getMessageValidationErrorDisplayed());
        Assert.assertEquals(
                contactUsPage.getMessageValidationErrorDisplayed(),
                expectederrorforMsgLengthValidation,
                "❌ Error message mismatch"
        );
    }



}
