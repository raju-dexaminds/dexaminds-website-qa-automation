@AboutUs
Feature: About Us Page UI and Content Validation

  Background:
    Given user navigates to homePage_url
    And user clicks on About Us link

  @smoke @positive
  Scenario: Verify About Us page loads successfully
    Then verify About Us page title is correct
    And verify About Us page URL is correct

  @positive
  Scenario: Verify About Us main heading is visible
    Then verify About Us main heading is visible

  @positive
  Scenario: Verify company description section is visible
    Then verify company description content is displayed

  @positive
  Scenario: Verify mission and vision section is visible
    Then verify mission and vision section are visible

  @positive
  Scenario: Verify navigation back to Home page from About Us
    When user clicks on Home link
    Then verify Home page is displayed

