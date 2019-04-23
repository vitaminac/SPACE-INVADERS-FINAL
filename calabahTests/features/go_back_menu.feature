Feature: Go back to the menu

  Scenario: As a player I want to terminate game ealier
    Given I enter text "Usuario" into field with id "name"
    And I go back
    And I enter text "19" into field with id "age"
    And I go back
    When I press "confirm"
    And I go back
    Then I should see "Confirmo"