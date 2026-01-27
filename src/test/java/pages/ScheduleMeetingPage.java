package pages;

import com.microsoft.playwright.Locator;
import config.PlaywrightDriver;

import java.util.ArrayList;
import java.util.List;


public class ScheduleMeetingPage {

    public boolean isScheduleMeetingVisible() {
        return PlaywrightDriver.isVisible("contact.scheduleMeetingBtn");
    }

    public String getCurrentURL() {
        return PlaywrightDriver.getPage().url();
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
        PlaywrightDriver.log("📅 Selecting date: " + date);
        PlaywrightDriver.waitForTime(15000);
        PlaywrightDriver.selectDateFromCalendar(date);

    }
    public void smartSelectSlot(String preferredSlot) {
        List<String> slots = getAvailableTimeSlots();

        if (slots.isEmpty()) {
            throw new RuntimeException("No available slots for this date");
        }

        if (slots.contains(preferredSlot)) {
            selectTimeSlot(preferredSlot);
            return;
        }

        // Pick another available slot
        String fallback = slots.get(0);
        selectTimeSlot(fallback);
        PlaywrightDriver.log("⚠ Preferred slot not available. Selected: " + fallback);
    }
    public static void selectTimeSlot(String time) {
        try {
            PlaywrightDriver.log("⏰ Selecting time slot: " + time);

            String xpath = String.format(PlaywrightDriver.getLocator("booking.timeXpath"), time);
            PlaywrightDriver.clickByXpath(xpath);

            PlaywrightDriver.log("✅ Time slot selected: " + time);
        } catch (Exception e) {
            PlaywrightDriver.setLastError(e);
            throw e;
        }
    }
    public String selectAnyAvailableSlot() {
        List<String> slots = getAvailableTimeSlots();

        if (slots.isEmpty()) {
            throw new RuntimeException("No available slots");
        }

        String selected = slots.get(0);
        selectTimeSlot(selected);
        return selected;
    }

    public List<String> getAvailableTimeSlots() {
        List<String> slots = new ArrayList<>();
        Locator slotElements = PlaywrightDriver.getPage().locator(PlaywrightDriver.getLocator("booking.availableSlots"));

        int count = slotElements.count();
        for (int i = 0; i < count; i++) {
            String time = slotElements.nth(i).innerText().trim();
            slots.add(time);
        }
        PlaywrightDriver.log("Available slots are " + slots);
        return slots;
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
