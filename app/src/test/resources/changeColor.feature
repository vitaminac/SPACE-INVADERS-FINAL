
Feature: The invader change its color
  In order to make the ui more attractive
  As a user
  I want the invader to change its color

  Background:
    Given a user name
    And being more than 13 years old

  Scenario: a bullet hit a defense block
    Given a bullet shoot by a spaceship
    And impact the defense block
    When have a color
    Then change to the reverse