
Feature: Kill a invader with the finger
  In order to decrease the game difficult
  As a user
  I want to be able to kill a invader with the finger

  Background:
    Given a user name

  Scenario: touch a invader on the screen
    Given a start game
    When you touch a invader with the finger
    Then the invader dies