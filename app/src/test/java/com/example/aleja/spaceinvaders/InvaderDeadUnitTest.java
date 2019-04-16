package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.StepDef.laser;
import static com.example.aleja.spaceinvaders.StepDef.marcianito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;

public class InvaderDeadUnitTest {
    @When("^The bullet hit a invader$")
    public void the_bullet_hit_a_invader() {
        doCallRealMethod().when(marcianito).onCollide(any(Laser.class));
        marcianito.onCollide(laser);
    }
}
