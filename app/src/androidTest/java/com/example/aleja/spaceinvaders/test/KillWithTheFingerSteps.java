package com.example.aleja.spaceinvaders.test;

import com.example.aleja.spaceinvaders.Marcianito;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.test.StepsDef.vista;
import static org.junit.Assert.assertFalse;

public class KillWithTheFingerSteps {
    static Marcianito marcianito;

    @And("^There is a invader$")
    public void there_is_a_invader() {
        marcianito = vista.getMarcianito()[0];
    }

    @When("^I touch a invader with the finger$")
    public void i_touch_a_invader_with_finger() {
        vista.onTouchDown(marcianito.getX(), marcianito.getY());
    }

    @Then("^The invader dies$")
    public void the_invader_dies() {
        assertFalse(marcianito.isVisible());
    }
}
