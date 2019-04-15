package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import static org.mockito.Mockito.mock;

public class ChangeColorUnitTest {
    Bloque block;

    @And("^There is a defense block$")
    public void there_is_a_defense_block() {
        this.block = mock(Bloque.class);
    }

    @When("^The bullet hit the defense block$")
    public void the_bullet_hit_the_defense_block() {

    }
}
