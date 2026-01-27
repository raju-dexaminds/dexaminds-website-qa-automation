Feature: Explore Dropdown Validation

  Background:
    Given user navigates to homePage_url

  Scenario: Verify explore dropdown displays all values
    When user hovers on explore dropdown
    Then all explore options should be visible

  Scenario Outline: Verify navigation for each explore
    When user hovers on explore dropdown
    And user selects a "<exploreType>"
    Then verify heading should be "<HeadingTitle>"

    Examples:
      | exploreType | HeadingTitle                      |
      | APIs        | Powerful APIs for Modern Business |
      | Blogs       | Latest Blog Posts                 |
