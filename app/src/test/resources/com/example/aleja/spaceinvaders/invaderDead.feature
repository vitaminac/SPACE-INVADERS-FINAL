
Feature: The invader die with a bullet
  In order to make the game funnier
  As a user
  I want the kill a invader with a bullet

  Scenario: a bullet hit a invader
    Given There is a lethal bullet shoot by a spaceship
    And There is a invader
    When The bullet hit a invader
    Then The invader dies