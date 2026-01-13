@Careers
Feature: Careers – Job Positions Verification and Application

  Background:
    Given user navigates to homePage_url
    Given user clicks on Careers link

  # -------------------------------------------
  # Careers Page Validation
  # -------------------------------------------

  @Positive
  Scenario: Verify Careers page loads successfully
    Then verify Careers page URL is correct
    And View Open Positions button should be visible

  @Positive
  Scenario: Verify Open Positions section is displayed
    When user clicks on View Positions
    Then Open Positions section should be visible
    And available job positions should be displayed

  # -------------------------------------------
  # Job Positions Verification
  # -------------------------------------------

  @Positive
  Scenario: Verify all expected job positions are displayed
    When user clicks on View Positions
    Then should display available job positions




  # -------------------------------------------
  # Job Details Page Validation
  # -------------------------------------------

  @Positive
  Scenario Outline: Verify Job Details page opens for specific job
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    Then Job Details page should load successfully


    Examples:
      | JobTitle                   |
      | Senior Full Stack Developer |
      | ReactJS Developer          |
      | DevOps Engineer            |
      | UI/UX Designer             |
      | AI/ML Engineer             |

  # -------------------------------------------
  # Job Application – Positive Flow
  # -------------------------------------------

  @Positive
  Scenario Outline: Submit Job Application for specific job position
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    And user clicks on Apply Job
    And user enters valid job application details
    And user uploads resume "<ResumeFile>"
    And user submits the job application
    Then Job Application confirmation message "Thank you for your application! We'll get back to you soon." should be displayed

    Examples:
      | JobTitle                    | ResumeFile       |
      | DevOps Engineer             | Resume.pdf       |
#      | ReactJS Developer           | large_resume.docx|

  # -------------------------------------------
  # Job Application – Negative Scenarios
  # -------------------------------------------


  @Negative
  Scenario Outline: Upload invalid resume format for specific job
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    And user clicks on Apply Job
    And user uploads resume "<ResumeFile>"
    Then error message "Invalid file type. Only PDF and Word documents are allowed" should be displayed for invalid resume

    Examples:
      | JobTitle                    | ResumeFile   |
      | Senior Full Stack Developer | resume.png   |
#      | ReactJS Developer           | resume.jpg   |
#      | DevOps Engineer             | resume.zip   |
#      | UI/UX Designer              | resume.exe   |
      | AI/ML Engineer              | datatable.txt   |


  @Negative
  Scenario Outline: Upload resume exceeding size limit for specific job
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    And user clicks on Apply Job
    And user uploads resume "<ResumeFile>"
    Then error message "File size exceeds 5MB limit" should be displayed for resume size limit
    Examples:
      | JobTitle                    | ResumeFile       |
      | ReactJS Developer           | 10mb.docx|
#      | Senior Full Stack Developer | large_resume.pdf |


