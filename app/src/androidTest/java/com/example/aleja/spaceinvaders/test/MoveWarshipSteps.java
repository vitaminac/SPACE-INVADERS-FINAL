package com.example.aleja.spaceinvaders.test;

import com.example.aleja.spaceinvaders.BotonM;
import com.example.aleja.spaceinvaders.Nave;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.test.StepsDef.vista;
import static org.junit.Assert.assertNotEquals;

public class MoveWarshipSteps {
    Nave nave;
    BotonM botonM;

    @And("^There is a button$")
    public void there_is_a_button() {
        botonM = (BotonM) (vista.getTouchableGameObjects().get(1));
    }

    @Given("^There is a warship$")
    public void there_is_a_warship() {
        nave = vista.getNave();
    }

    @When("^I press the button")
    public void i_press_the_bottom() {
        vista.onTouchDown(-895, 460);
    }

    @Then("^The warship will change state$")
    public void the_warship_will_change_state() {
        assertNotEquals(Nave.PARADA, this.nave.getShipMoving());
    }
}
