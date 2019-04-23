
Feature: Control BotonM
  In order to interact
  As a user
  I can press the button to ove the warship

  Scenario: press buttom
    Given There is a warship
    And There is a button
    When I press the button
    Then The warship will change state