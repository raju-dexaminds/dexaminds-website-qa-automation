Feature: Schedule a Meeting from Contact Page

  Background:
    Given user navigates to homePage_url
    And user clicks on Contact US Button in home page

  # ---------------- POSITIVE SCENARIOS ----------------


  Scenario: Verify Schedule a Meeting button is visible on Contact page
    Then Schedule a Meeting button should be visible



  Scenario: Verify Outlook booking page URL is correct
    When user clicks on Schedule a Meeting
    Then booking page URL should be "https://outlook.office.com/book/connectwith@dexaminds.com/?ismsaljsauthenabled"


  Scenario: Verify user can submit booking with valid details
    When user clicks on Schedule a Meeting
    And user selects a date "2026-12-31"
    And user selects a time slot "10:30 AM"
    And user enters first and last name "Raju"
    And user enters email "raju.b@dexaminds.com"
    And user enters address "Hyderabad"
    And user enters phone number "9392578835"
    And user enters special request ""
    And user clicks on Book button
    Then booking confirmation message "Thank you for booking with us! You will get a confirmation message in email shortly" should be displayed






  # ---------------- NEGATIVE SCENARIOS ----------------

  Scenario: Submit booking without name
    When user clicks on Schedule a Meeting
    And user selects a date "2026-11-15"
    And user selects a time slot "10:30 AM"
    And user enters email "raju@test.com"
    And user clicks on Book button
    Then error message should be displayed for name


  Scenario: Submit booking without email
    When user clicks on Schedule a Meeting
    And user selects a date "2026-11-15"
    And user selects a time slot "10:30 AM"
    And user enters first and last name "Raju"
    And user clicks on Book button
    Then error message should be displayed for email


  Scenario: Submit booking with invalid email
    When user clicks on Schedule a Meeting
    And user selects a date "2026-11-15"
    And user selects a time slot "10:30 AM"
    And user enters first and last name "Raju"
    And user enters email "invalid-email"
    And user clicks on Book button
    Then error message  should be displayed for Invalid Email



  Scenario: Submit booking without selecting time slot
    When user clicks on Schedule a Meeting
    And user selects a date "2026-11-15"
    And user enters first and last name "Raju"
    And user enters email "raju@test.com"
    And user clicks on Book button
    Then error message "Preferred date and time" should be displayed for selecting Time

