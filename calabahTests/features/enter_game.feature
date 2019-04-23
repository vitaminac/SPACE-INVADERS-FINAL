Feature: Intruduce name and age

  Scenario: As a valid user I need to introduce my data
    Given I enter text "Usuario" into field with id "name"
    And I go back
    And I enter text "19" into field with id "age"
    And I go back
    When I press "confirm"
    Then I should not see "Confirmo"
