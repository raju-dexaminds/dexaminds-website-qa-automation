package stepdefs;

import config.PlaywrightDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CareersPage;
import pages.JobApplicationPage;

import java.util.List;

public class CareersSteps {

    CareersPage careersPage = new CareersPage();
    JobApplicationPage jobPage = new JobApplicationPage();

    @Given("user clicks on Careers link")
    public void user_clicks_on_Careers_links() {

        careersPage.clickCareersUs();
    }

    @Then("verify Careers page URL is correct")
    public void verify_careers_page_URL_is_correct() {


        String actualUrl = careersPage.getCareersPageURL();
        String expectedUrl = PlaywrightDriver.getConfig("career.ExpectedUrl");

        PlaywrightDriver.log("Actual URL: " + actualUrl);
        PlaywrightDriver.log("Expected URL: " + expectedUrl);

        Assert.assertTrue(actualUrl.contains(expectedUrl),
                "❌ Career page URL incorrect");
    }

    @Then("View Open Positions button should be visible")
    public void view_positions_button_should_be_visible() {
        PlaywrightDriver.log("Checking if View Open Positions button is visible");

        Assert.assertTrue(
                careersPage.isViewPositionsButtonVisible(),
                "❌ View Positions button is not visible"
        );
    }

    @When("user clicks on View Positions")
    public void click_view_positions() {

        careersPage.clickViewPositions();
    }

    @Then("Open Positions section should be visible")
    public void verify_open_positions() {
        PlaywrightDriver.log("Verifying Open Positions section is visible");

        Assert.assertTrue(
                careersPage.isOpenPositionsVisible(),
                "❌ Open Positions not visible"
        );
    }



    @When("user clicks on View Details for {string}")
    public void click_view_details(String jobTitle) {

        careersPage.clickViewDetailsForJob(jobTitle);
    }

    @Then("Job Details page should load successfully")
    public void verify_job_details_page() {
        PlaywrightDriver.log("Verifying Job Details page is loaded");

        Assert.assertTrue(
                jobPage.isApplyJobButtonVisible(),
                "❌ Job details page not loaded"
        );
    }

    @When("user clicks on Apply Job")
    public void click_apply_job() {

        jobPage.clickApplyJob();
    }

    @When("user enters valid job application details")
    public void enter_valid_details() {

        jobPage.fillValidApplication();
    }

    @When("user uploads resume {string}")
    public void user_uploads_resume(String resumeFileName) {

        PlaywrightDriver.uploadResume("job.resume", resumeFileName);
        PlaywrightDriver.log("User uploads resume file: " + resumeFileName);
    }

    @When("user submits the job application")
    public void submit_application() {

        jobPage.submitApplication();
    }

    @Then("Job Application confirmation message {string} should be displayed")

    public void check_confirmation_msg(String expectedSuccessMsg) {
        PlaywrightDriver.log("Expected  Msg  "+expectedSuccessMsg);
        PlaywrightDriver.log("Actual  Message "+jobPage.getMessageforJobApplicationSuccess());
        Assert.assertEquals(
                jobPage.getMessageforJobApplicationSuccess(),
                expectedSuccessMsg,
                "❌ Success message mismatch"
        );

    }



    @Then("error message {string} should be displayed for invalid resume")
    public void verify_resume_error(String expectederrorforinvalidresume) {
        PlaywrightDriver.log("Verifying error message for invalid resume");

        PlaywrightDriver.log("Expected error: " + expectederrorforinvalidresume);
        PlaywrightDriver.log("Actual error: " + jobPage.getResumeErrorDisplayed());

        Assert.assertEquals(
                jobPage.getResumeErrorDisplayed(),
                expectederrorforinvalidresume,
                "❌ Resume error not displayed"
        );
    }

    @Then("available job positions should be displayed")
    public void available_job_positions_should_be_displayed() {
        PlaywrightDriver.log("Verifying available job positions are displayed");

        Assert.assertTrue(
                careersPage.areJobPositionsDisplayed(),
                "❌ Job positions are not displayed"
        );
    }

    @Then("error message {string} should be displayed for resume size limit")
    public void error_message_should_be_displayed(String expectedMessage) {
        PlaywrightDriver.log("Verifying error message for resume size limit");

        PlaywrightDriver.log("Expected error: " + expectedMessage);
        PlaywrightDriver.log("Actual error: " + careersPage.getResumeSizeErrorMessage());

        Assert.assertEquals(
                careersPage.getResumeSizeErrorMessage(),
                expectedMessage,
                "❌ Resume size error message mismatch"
        );
    }

    @Then("should display available job positions")
    public void should_display_available_job_positions() {
        List<String> jobs = CareersPage.getAvailableJobPositions();

        Assert.assertTrue(jobs.size() > 0, "No job positions found!");

        PlaywrightDriver.log("Available Job Positions:");
        for (String job : jobs) {
            PlaywrightDriver.log(" - " + job);
        }
    }

}
