package com.example.aleja.spaceinvaders.test;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.test.StepsDef.marcianito;
import static com.example.aleja.spaceinvaders.test.StepsDef.vista;
import static org.junit.Assert.assertFalse;

public class KillWithTheFingerSteps {
    @When("^I touch a invader with the finger$")
    public void i_touch_a_invader_with_finger() {
        vista.onTouchDown(marcianito.getX(), marcianito.getY());
    }

    @Then("^The invader dies$")
    public void the_invader_dies() {
        assertFalse(marcianito.isVisible());
    }
}
