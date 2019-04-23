
Feature: Score of game
  In order to to make the game more competitive
  As a user
  I want see my puntuation and change with interaction

  @smoke
  @e2e
  Scenario: add puntuation
    Given There is a running game
    And There is a bullet shoot by a spaceship
    And There is a invader
    And I have zero point
    When The bullet hit a invader
    Then I will get hundred point more