package pages;

import com.microsoft.playwright.Frame;
import config.PlaywrightDriver;



public class ScheduleMeetingPage {

    public boolean isScheduleMeetingVisible() {
        return PlaywrightDriver.isVisible("contact.scheduleMeetingBtn");
    }



    public boolean isBookingPageOpened() {
        return PlaywrightDriver.isVisible("booking.page.container");
    }

    public String getCurrentURL() {
        return PlaywrightDriver.getPage().url();
    }

    public boolean areSlotsVisible() {
        return PlaywrightDriver.isVisible("booking.availableSlots");
    }


    public void confirmBooking() {
        PlaywrightDriver.click("booking.confirmBtn");
    }

    public boolean isBookingSuccess() {
        return PlaywrightDriver.isVisible("booking.successMsg");
    }



    public void clickScheduleMeetingAndSwitchTab() {
        PlaywrightDriver.switchToNewTabAfterClick("contact.scheduleMeetingBtn");
    }


    public void enterName(String name) {
        PlaywrightDriver.log("Entering name");
        PlaywrightDriver.type("booking.name", name);
    }

    public void enterEmail(String email) {
        PlaywrightDriver.log("Entering email");
        PlaywrightDriver.type("booking.email", email);
    }

    public void enterAddress(String address) {
        PlaywrightDriver.log("Entering address");
        PlaywrightDriver.type("booking.address", address);
    }

    public void enterPhone(String phone) {
        PlaywrightDriver.log("Entering phone");
        PlaywrightDriver.type("booking.phone", phone);
    }

    public void enterSpecialRequest(String request) {
        PlaywrightDriver.log("Entering special request");
        PlaywrightDriver.type("booking.specialRequest", request);
    }

    public void clickBook() {
        PlaywrightDriver.log("Clicking Book button");
        PlaywrightDriver.click("booking.bookBtn");
    }


    public void selectDate(String date) {
        PlaywrightDriver.selectDateFromCalendar(date);
    }

    public void selectTimeSlot(String time) {
        PlaywrightDriver.TimeSlot(time);
    }

    public boolean isMessageforInvalidEmaill() {
        return PlaywrightDriver.isVisible(("booking.InvalidEmail"));
    }

    public String getMessageforTimeErrorDisplayed() {
        return PlaywrightDriver.getText("booking.errorMsg");
    }

    public String getMessageforBookingSuccess() {
        return PlaywrightDriver.getText("booking.successMsg");
    }
}
