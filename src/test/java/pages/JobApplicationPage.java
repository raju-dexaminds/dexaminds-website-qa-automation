package pages;

import config.PlaywrightDriver;

public class JobApplicationPage {

    public void fillValidApplication() {
        PlaywrightDriver.type("job.name", "Raju");
        PlaywrightDriver.type("job.email", "raju.b@dexaminds.com");
        PlaywrightDriver.type("job.message", "Testing Demo");
        PlaywrightDriver.log("User enters valid job application details Name, Email, Message");
    }


    public void clickApplyJob() {
        PlaywrightDriver.click("job.apply.button");
        PlaywrightDriver.log("User clicks on Apply Job button");
        PlaywrightDriver.scrollTo("job.submit");

    }

    public void submitApplication() {
        PlaywrightDriver.click("job.submit");
        PlaywrightDriver.log("User submits the job application");
        PlaywrightDriver.waitForTime(15000);
    }

    public String getResumeErrorDisplayed() {
        return PlaywrightDriver.getText(("job.error.resume"));
    }

    public boolean isApplyJobButtonVisible() {
        return PlaywrightDriver.isVisible("job.apply.button");
    }

    public String getMessageforJobApplicationSuccess() {
        return PlaywrightDriver.getText("job.confirmationMsg");
    }
}
