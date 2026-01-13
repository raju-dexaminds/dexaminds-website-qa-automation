package pages;

import config.PlaywrightDriver;

public class ContactUsPage {

    public void clickContactUs() {
        PlaywrightDriver.click("contact.button");
    }

    public boolean isContactButtonVisible() {
        return PlaywrightDriver.isVisible(
                PlaywrightDriver.getLocator("contact.button"));
    }



    public void clickSend() {
        PlaywrightDriver.log("Clicking Send button");
        PlaywrightDriver.click(
                "contact.send");
        PlaywrightDriver.log("Clicked Send button");
    }

//    public boolean isSuccessMessageDisplayed(String expectedMessage) {
//
//        try {
//            // Wait for success message to appear
//            PlaywrightDriver.isVisible(
//                    PlaywrightDriver.getLocator("contactUs.successMessage")
//            );
//
//            // Get actual text from UI
//            String actualMessage = PlaywrightDriver.getText(PlaywrightDriver.getLocator("contactUs.successMessage"));
//
//            // Compare with expected message from config
//            return actualMessage.equals(expectedMessage);
//
//        } catch (Exception e) {
//            return false;
//        }
//    }



    // ================= NAVIGATION =================


    public boolean isContactUsButtonClickable() {
        return PlaywrightDriver.isClickable("contactus.button");
    }

//    public boolean isContactUsPageLoaded() {
//        return PlaywrightDriver.getPage().url().contains("contact");
//    }

    // ================= FIELD VISIBILITY =================

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


    // ================= FORM INPUT =================

    public void enterName(String name) {
        PlaywrightDriver.type("contact.name", name);
    }

    public void enterEmail(String email) {
        PlaywrightDriver.type("contact.email", email);
    }

    public void enterMessage(String message) {
        PlaywrightDriver.type("contact.message", message);
    }

    public void clickSendMessageButton() {
        PlaywrightDriver.click("contactus.send");
    }

    // ================= VALIDATIONS =================

    public String getSuccessMessageDisplayed() {
        return PlaywrightDriver.getText(("contact.success"));
    }



    public String getErrorMessageInvalidatorName() {
        return PlaywrightDriver.getText(("contact.error.message.forInvalidname"));

    }

    public boolean isInvalidNameErrorDisplayed() {
        return PlaywrightDriver.isVisible("contact.invalid.name");
    }

    public String getNameLengthErrorDisplayed() {
        return PlaywrightDriver.getText(("contact.error.message.forNamelengthvalidation"));
    }

    public String getMessageValidationErrorDisplayed() {
        return PlaywrightDriver.getText(("contact.error.message.forMsglengthvalidation"));
    }



    // ================= TITLE =================

//    public String getContactUsPageTitle() {
//        return PlaywrightDriver.getTitle();
//    }


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
