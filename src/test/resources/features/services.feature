Feature: Services Dropdown Validation

  Background:
    Given user navigates to homePage_url

  Scenario: Verify Services dropdown displays all values
    When user hovers on Services dropdown
    Then get all services options
  Scenario Outline: Verify navigation for each service

    When user hovers on Services dropdown
    And user selects "<serviceName>"
    Then verify heading is "<HeadingTitle>"
    Examples:
      | serviceName            | HeadingTitle           |
      | All Services           | Our Services           |
      | AI Engineering         | AI Engineering         |
      | Software Development   | Software Development   |
      | Quality Assurance      | Quality Assurance      |
      | DevOps & CI/CD         | DevOps & CI/CD         |
      | Cloud Solutions        | Cloud Solutions        |
      | Digital Transformation | Digital Transformation |
