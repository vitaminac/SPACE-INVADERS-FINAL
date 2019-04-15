package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.StepDef.marcianito;

public class InvaderDeadUnitTest {
    private Laser laser;

    @Given("^There is a lethal bullet shoot by a spaceship$")
    public void there_is_a_lethal_bullet_shoot_by_a_spaceship() throws Throwable {
        this.laser = new Laser(100);
    }

    @When("^The bullet hit a invader$")
    public void the_bullet_hit_a_invader() {
        marcianito.onCollide(marcianito);
    }
}
