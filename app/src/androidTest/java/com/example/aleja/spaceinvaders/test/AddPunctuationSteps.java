package com.example.aleja.spaceinvaders.test;

import com.example.aleja.spaceinvaders.State;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import static org.junit.Assert.assertEquals;

public class AddPunctuationSteps {
    @And("^I have zero point$")
    public void i_have_zero_point() {
        State.puntuacion = 0;
    }

    @Then("^I will get hundred point more$")
    public void i_will_get_hundred_point_more() {
        assertEquals(100, State.puntuacion);
    }
}
