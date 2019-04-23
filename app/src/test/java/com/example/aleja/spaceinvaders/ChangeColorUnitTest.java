package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.State.Red;
import static com.example.aleja.spaceinvaders.StepDef.invaders;
import static com.example.aleja.spaceinvaders.StepDef.laser;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class ChangeColorUnitTest {
    Bloque block;

    @And("^There is a defense block$")
    public void there_is_a_defense_block() {
        this.block = mock(Bloque.class);
        doCallRealMethod().when(block).onCollide(any());
        when(block.getVisibility()).thenReturn(true);
        when(invaders.getBloques()).thenReturn(new Bloque[]{this.block});
    }

    @And("^The color is red$")
    public void the_color_is_red() {
        State.selectColor = Red;
    }

    @When("^The bullet hit the defense block$")
    public void the_bullet_hit_the_defense_block() {
        doCallRealMethod().when(invaders).laserMayHitWithBlock(any(Laser.class));
        invaders.laserMayHitWithBlock(laser);
    }

    @Then("^The color is not red$")
    public void the_color_is_not_red() {
        assertNotEquals(Red, State.selectColor);
    }
}
