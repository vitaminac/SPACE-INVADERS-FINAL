
  Feature: The bullets will bounce
    In order to increase the difficult
    As a user
    I want to be able to activate bullet bounce mode

    Background:
      Given a user name
      And activate the bullet bounce mode

    Scenario: bullet impact the bottom and top of the screen
      Given a bullet shoot by a spaceship
      And impact to the screen
      When it is the bottom or the top
      Then change direction to the opposite
