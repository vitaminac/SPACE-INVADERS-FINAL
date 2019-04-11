
Feature: Appearance of the spontaneous invader
  In order to increase the game difficult
  As a user
  I want a spontaneous invader to appear every 10 seconds

  Background:
    Given a user name

  Scenario: every 10 seconds
    Given a start game
    When 10 seconds have passed
    Then the spontaneous invader appear