Feature: Play without violence
    
Scenario: As a baby I want to play the game without violence
    Given I enter text "Usuario" into field with id "name"
    And I go back
    And I enter text "10" into field with id "age"
    And I go back
    When I press "confirm"
    And I wait for 5 seconds
    And I click on screen 50% from the left and 50% from the top
    And I wait for 5 seconds
    Then I wait for the "SpaceInvaders" screen to appear