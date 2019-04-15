package com.example.aleja.spaceinvaders;

import android.content.Context;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class InvaderDeadUnitTest {
    private Laser laser;
    private Marcianito marcianito;

    @Given("^There is a lethal bullet shoot by a spaceship$")
    public void there_is_a_lethal_bullet_shoot_by_a_spaceship() throws Throwable {
        this.laser = new Laser(100);
    }

    @And("There is a invader")
    public void there_is_a_invader() {
        this.marcianito = new Marcianito(null, 100, 100);
    }


    @When("^The bullet hit a invader$")
    public void the_bullet_hit_a_invader() {
        marcianito.onCollide(this.marcianito);
    }

    @Then("^The invader dies$")
    public void my_belly_should_growl() {
        assertFalse(marcianito.isVisible());
    }
}
