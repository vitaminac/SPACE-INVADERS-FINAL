Feature: End game

  Scenario: As a player I want the game should end without interaction
    Given I enter text "Usuario" into field with id "name"
    And I go back
    And I enter text "19" into field with id "age"
    And I go back
    When I press "confirm"
    And I wait for 5 seconds
    And I click on screen 50% from the left and 50% from the top
    Then I wait for the "MenuActivity" screen to appear