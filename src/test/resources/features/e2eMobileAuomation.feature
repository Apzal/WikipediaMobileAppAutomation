@mobile
Feature: E2E Mobile Automation

  Scenario: Task1-Access icons-My List, History and Nearby
    Given I launch the app
    And I scroll down
    And I click on footer icons mylist history and nearby with 3 seconds pause
    And I click on the explore icon
    Then I scroll to the top and verify first topic as "In the news"


  Scenario: Task2- Search functionality
    Given I launch the app
    And I search for "New York"
    And I validate the results for "New York"
    And I clear the search text
    Then I verify I am in home screen

  Scenario: Task3- Toggle off all settings
    Given I launch the app
    And I navigate to settings page
    And I toggle off for all below settings
    |Show images|
    |Show link previews|
    |Send usage reports|
    |Send crash reports|
    And I navigate back to home screen
    Then I verify I am in home screen
