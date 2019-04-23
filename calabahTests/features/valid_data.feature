Feature: Valid User Input
    
Scenario: As a maintainer I want the user data to be valid
    Given I enter text "" into field with id "name"
    And I go back
    And I enter text "" into field with id "age"
    And I go back
    When I press "confirm"
    Then I should see "Confirmo"