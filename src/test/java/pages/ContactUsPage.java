package pages;

import config.PlaywrightDriver;

public class ContactUsPage {

    public void clickContactUs() {
        PlaywrightDriver.click("contact.button");
    }
    public void clickSend() {
        PlaywrightDriver.log("Clicking Send button");
        PlaywrightDriver.click(
                "contact.send");
        PlaywrightDriver.log("Clicked Send button");
    }

    public boolean isNameFieldVisibleAndEnabled() {
        return PlaywrightDriver.isVisible("contact.name");
    }

    public boolean isEmailFieldVisibleAndEnabled() {
        return PlaywrightDriver.isVisible("contact.email");
    }

    public boolean isMessageFieldVisibleAndEnabled() {
        return PlaywrightDriver.isVisible("contact.message");
    }

    public boolean isSendMessageButtonVisibleAndEnabled() {
        return PlaywrightDriver.isVisible("contact.send");
    }

    public void enterName(String name) {
        PlaywrightDriver.type("contact.name", name);
    }

    public void enterEmail(String email) {
        PlaywrightDriver.type("contact.email", email);
    }

    public void enterMessage(String message) {
        PlaywrightDriver.type("contact.message", message);
    }

    public String getSuccessMessageDisplayed() {
        return PlaywrightDriver.getText(("contact.success"));
    }


    public String getErrorMessageInvalidatorName() {
        return PlaywrightDriver.getText(("contact.error.message.forInvalidname"));

    }

    public String getNameLengthErrorDisplayed() {
        return PlaywrightDriver.getText(("contact.error.message.forNamelengthvalidation"));
    }

    public String getMessageValidationErrorDisplayed() {
        return PlaywrightDriver.getText(("contact.error.message.forMsglengthvalidation"));
    }


    public boolean isContactUsPageOpened() {

        return PlaywrightDriver.isVisible("contact.name");
    }

    public String getErrorMessageInvalidatorMessage() {
        return PlaywrightDriver.getText(("contact.error.message.forInvalidMsg"));
    }

    public String getErrorMessageInvalidatorEmail() {
        return PlaywrightDriver.getText(("contact.error.message.forInvalidEmail"));
    }
}
