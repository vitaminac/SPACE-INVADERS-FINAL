
Feature: The invader change its color
  In order to make the ui more attractive
  As a user
  I want the invader to change its color

  Scenario: a bullet hit a defense block
    Given There is a running game
    And There is a bullet shoot by a spaceship
    And There is a defense block
    And The color is red
    When The bullet hit the defense block
    Then The color is not red