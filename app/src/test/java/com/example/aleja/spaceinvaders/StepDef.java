package com.example.aleja.spaceinvaders;

import android.graphics.RectF;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

public class StepDef {
    static VistaSpaceInvaders invaders;
    static Marcianito marcianito;
    static Laser laser;

    @Given("^There is a running game$")
    public void there_is_a_running_game() throws Throwable {
        invaders = mock(VistaSpaceInvaders.class);
        doCallRealMethod().when(invaders).onTouchDown(anyFloat(), anyFloat());
    }

    @And("^There is a bullet shoot by a spaceship$")
    public void there_is_a_bullet_shoot_by_a_spaceship() throws Throwable {
        laser = new Laser(100);
        laser.shoot(0, 0, 0);
    }

    @And("^There is a invader$")
    public void there_is_a_invader() {
        marcianito = mock(Marcianito.class);
        when(marcianito.getX()).thenReturn(250.0f);
        when(marcianito.getY()).thenReturn(250.0f);
        doCallRealMethod().when(marcianito).onTouchDown(anyFloat(), anyFloat());
        when(invaders.getTouchableGameObjects()).thenAnswer(new Answer<List<Marcianito>>() {
            @Override
            public List<Marcianito> answer(InvocationOnMock invocation) {
                return Collections.singletonList(marcianito);
            }
        });
    }

    @Then("^The invader dies$")
    public void the_invader_dies() {
        assertFalse(marcianito.isVisible());
    }
}
