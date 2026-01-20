@ContactUs @Regression
Feature: Contact Us Navigation, UI and Validation

  Background:
    Given user navigates to homePage_url
    And user clicks on Contact US Button in home page

  @Smoke @UI
  Scenario: Verify Contact Us page loads successfully
    Then verify Contact Us page loads successfully

  @UI
  Scenario: Verify Name field is visible and enabled
    Then verify Name field is visible and enabled

  @UI
  Scenario: Verify Email field is visible and enabled
    Then verify Email field is visible and enabled

  @UI
  Scenario: Verify Message field is visible and enabled
    Then verify Message field is visible and enabled

  @UI
  Scenario: Verify Send Message button is visible and enabled
    Then verify Send Message button is visible and enabled

  @Smoke @Positive
  Scenario: Submit Contact form with valid data
    When user enters valid Name
    And user enters valid Email
    And user enters valid Message
    And user clicks Send Message button
    Then verify success message "Thank you for your message! We'll get back to you soon." is displayed

  @Negative @EmailValidation
  Scenario Outline: Verify error message should be displayed for Invalid email format
    When user enters email as "<email>"
    Then error message "Invalid email format" should be displayed for Invalid email format
    Examples:
      | email           |
      | test123         |
      | test@           |
      | test @gmail.com |

  @Positive @EmailValidation
  Scenario: Submit Contact form with uppercase email
    When user enters valid Name
    And user enters email as "TEST@GMAIL.COM"
    And user enters valid Message
    And user clicks Send Message button
    Then verify success message "Thank you for your message! We'll get back to you soon." is displayed

  @Negative @NameValidation
  Scenario Outline: Verify error message should be displayed for Invalid Name format
    When user enters name as "<name>"
    Then error message "Name can only contain letters and spaces" should be displayed for invalid Name
    Examples:
      | name  |
      | 12345 |
      | @#$%^ |

  @Negative @NameValidation
  Scenario Outline: Verify error message should be displayed for Name length validation
    When user enters name as "<name>"
    Then error message "Name must be at least 3 characters" should be displayed for Name length validation
    Examples:
      | name |
      | a    |
      | ab   |

  @Negative @MessageValidation
  Scenario: Verify error message should be displayed for Message length validation
    When user enters message as "H"
    Then error message "Message must be at least 2 characters" should be displayed for Message length validation

  @Negative @MessageValidation
  Scenario: Verify error message should be displayed for Invalid Message
    When user enters message as "@#$%^"
    Then error message "Message contains invalid characters" should be displayed for invalid Message


