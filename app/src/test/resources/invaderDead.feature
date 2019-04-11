
Feature: The invader die with a bullet
  In order to make the game funnier
  As a user
  I want the kill a invader with a bullet

  Background:
    Given a user name
    And being more than 13 years old

  Scenario: a bullet hit a invader
    Given a bullet shoot by a spaceship
    And bullet is lethal
    When have bullet hit a invader
    Then the invader dies