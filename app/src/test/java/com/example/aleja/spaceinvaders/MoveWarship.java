package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class MoveWarship {
    Nave nave;
    BotonM botonM;

    @And("^There is a button$")
    public void there_is_a_button() {
        botonM = mock(BotonM.class);
        when(botonM.getNave()).thenReturn(nave);
        when(botonM.getLength()).thenReturn(15f);
        when(botonM.getHeight()).thenReturn(15f);
        when(botonM.getDirection()).thenReturn(1);
        doCallRealMethod().when(botonM).onTouchDown(anyFloat(), anyFloat());
    }

    @Given("^There is a warship$")
    public void there_is_a_warship() {
        nave = mock(Nave.class);
        doCallRealMethod().when(nave).setMovementState(anyInt());
        doCallRealMethod().when(nave).getShipMoving();
    }

    @When("^I press the button")
    public void i_press_the_bottom() {
        botonM.onTouchDown(10, 10);
    }

    @Then("^The warship will change state$")
    public void the_warship_will_change_state() {
        assertNotEquals(this.nave.PARADA, this.nave.getShipMoving());
    }
}
