
Feature: Kill a invader with the finger
  In order to decrease the game difficult
  As a user
  I want to be able to kill a invader with the finger

  Scenario: touch a invader on the screen
    Given There is a running game
    And There is a invader
    When I touch a invader with the finger
    Then The invader dies