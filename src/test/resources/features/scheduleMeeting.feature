Feature: Schedule a Meeting from Contact Page

  Background:
    Given user navigates to homePage_url
    And user clicks on Contact US Button in home page

  Scenario: Verify Schedule a Meeting button is visible on Contact page
    Then Schedule a Meeting button should be visible

  Scenario: Verify Outlook booking page URL is correct
    When user clicks on Schedule a Meeting
    Then booking page URL should be "https://outlook.office.com/book/connectwith@dexaminds.com/?ismsaljsauthenabled"

  @Positive
  Scenario: Verify user can submit booking with valid details
    When user clicks on Schedule a Meeting
    And user selects a date "2026-12-25"
    And user selects a time slot "9:00 AM"
    And user enters first and last name "Raju"
    And user enters email "raju.b@dexaminds.com"
    And user enters address "Hyderabad"
    And user enters phone number "9392578835"
    And user enters special request ""
    And user clicks on Book button
    Then booking confirmation message "Thank you for booking with us! You will get a confirmation message in email shortly" should be displayed

  @Positive
  Scenario: Book available time slot for a selected date
    When user clicks on Schedule a Meeting
    And user selects a date "2026-10-02"
    And user checks available slots and selects any available slot
    And user enters first and last name "Raju"
    And user enters email "raju.b@dexaminds.com"
    And user enters address "Hyderabad"
    And user enters phone number "9392578835"
    And user enters special request ""
    And user clicks on Book button
    Then booking confirmation message "Thank you for booking with us! You will get a confirmation message in email shortly" should be displayed


  @Negative
  Scenario: Verify that when a user selects an already booked time slot, the system automatically books the next available time slot.
    When user clicks on Schedule a Meeting
    And user selects a date "2026-10-02"
    And user selects a time slot "9:00 AM"
    And user enters first and last name "Raju"
    And user enters email "raju.b@dexaminds.com"
    And user enters address "Hyderabad"
    And user enters phone number "9392578835"
    And user enters special request ""
    And user clicks on Book button
    Then booking confirmation message "Thank you for booking with us! You will get a confirmation message in email shortly" should be displayed



  @Negative
  Scenario: Submit booking without name
    When user clicks on Schedule a Meeting
    And user selects a date "2026-10-25"
    And user selects a time slot "10:30 AM"
    And user enters email "raju@test.com"
    And user clicks on Book button
    Then error message should be displayed for name

  @Negative
  Scenario: Submit booking without email
    When user clicks on Schedule a Meeting
    And user selects a date "2026-12-25"
    And user selects a time slot "10:30 AM"
    And user enters first and last name "Raju"
    And user clicks on Book button
    Then error message should be displayed for email

  @Negative
  Scenario: Submit booking with invalid email
    When user clicks on Schedule a Meeting
    And user selects a date "2026-12-25"
    And user selects a time slot "10:30 AM"
    And user enters first and last name "Raju"
    And user enters email "invalid-email"
    And user clicks on Book button
    Then error message  should be displayed for Invalid Email

  @Negative
  Scenario: Submit booking without selecting time slot
    When user clicks on Schedule a Meeting
    And user selects a date "2026-12-25"
    And user enters first and last name "Raju"
    And user enters email "raju@test.com"
    And user clicks on Book button
    Then error message "Preferred date and time" should be displayed for selecting Time

