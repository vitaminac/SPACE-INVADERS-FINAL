package com.example.aleja.spaceinvaders.test;

import com.example.aleja.spaceinvaders.Nave;
import com.example.aleja.spaceinvaders.State;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.test.StepsDef.*;
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

    @When("^The bullet hit a invader$")
    public void the_bullet_hit_a_invader() {
        laser.shoot(marcianito.getX(), marcianito.getY(), Nave.UP);
        laser.update(100);
        vista.update();
    }
}
