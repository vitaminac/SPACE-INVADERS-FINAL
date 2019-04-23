package com.example.aleja.spaceinvaders.test;

import com.example.aleja.spaceinvaders.Bloque;
import com.example.aleja.spaceinvaders.Nave;
import com.example.aleja.spaceinvaders.State;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.State.Red;
import static com.example.aleja.spaceinvaders.test.StepsDef.laser;
import static com.example.aleja.spaceinvaders.test.StepsDef.vista;
import static org.junit.Assert.assertNotEquals;

public class ChangeColorSteps {
    Bloque block;

    @And("^There is a defense block$")
    public void there_is_a_defense_block() {
        block = vista.getBloques()[0];
    }

    @And("^The color is red$")
    public void the_color_is_red() {
        State.selectColor = Red;
    }

    @When("^The bullet hit the defense block$")
    public void the_bullet_hit_the_defense_block() {
        laser.shoot(90, 440, Nave.DOWN);
        laser.update(100);
        vista.laserMayHitWithBlock(laser);
    }

    @Then("^The color is not red$")
    public void the_color_is_not_red() {
        assertNotEquals(Red, State.selectColor);
    }
}
