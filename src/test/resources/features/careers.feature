@Careers
Feature: Careers – Job Positions Verification and Application

  Background:
    Given user navigates to homePage_url
    Given user clicks on Careers link

  @Positive
  Scenario: Verify Careers page loads successfully
    Then verify Careers page URL is correct
    And View Open Positions button should be visible

  @Positive
  Scenario: Verify Open Positions section is displayed
    When user clicks on View Positions
    Then Open Positions section should be visible
    And available job positions should be displayed

  @Positive
  Scenario: Verify all expected job positions are displayed
    When user clicks on View Positions
    Then should return available job positions

  @Positive
  Scenario Outline: Verify Job Details page opens for specific job
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    Then Job Details page should load successfully
    Examples:
      | JobTitle                    |
      | Senior Full Stack Developer |
      | ReactJS Developer           |
      | DevOps Engineer             |
      | UI/UX Designer              |
      | AI/ML Engineer              |

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
      | JobTitle        | ResumeFile |
      | DevOps Engineer | Resume.pdf |

  @Negative
  Scenario Outline: Upload invalid resume format for specific job
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    And user clicks on Apply Job
    And user uploads resume "<ResumeFile>"
    Then error message "Invalid file type. Only PDF and Word documents are allowed" should be displayed for invalid resume

    Examples:
      | JobTitle                    | ResumeFile    |
      | Senior Full Stack Developer | resume.png    |
      | AI/ML Engineer              | datatable.txt |

  @Negative
  Scenario Outline: Upload resume exceeding size limit for specific job
    When user clicks on View Positions
    And user clicks on View Details for "<JobTitle>"
    And user clicks on Apply Job
    And user uploads resume "<ResumeFile>"
    Then error message "File size exceeds 5MB limit" should be displayed for resume size limit
    Examples:
      | JobTitle          | ResumeFile |
      | ReactJS Developer | 10mb.docx  |


  @Negative
  Scenario: Verify message when no job positions are available
    When user clicks on View Positions
    Then system should display "No job positions are available" message

