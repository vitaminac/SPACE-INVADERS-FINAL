package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.StepDef.laser;
import static com.example.aleja.spaceinvaders.StepDef.marcianito;

public class InvaderDeadUnitTest {
    @When("^The bullet hit a invader$")
    public void the_bullet_hit_a_invader() {
        marcianito.onCollide(laser);
    }
}
