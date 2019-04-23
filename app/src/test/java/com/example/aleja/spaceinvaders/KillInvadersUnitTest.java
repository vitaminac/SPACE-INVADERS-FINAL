package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.When;

import static com.example.aleja.spaceinvaders.StepDef.invaders;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KillInvadersUnitTest {
    @When("^I touch a invader with the finger$")
    public void i_touch_a_invader_with_the_finger() {
        invaders.onTouchDown(250.f, 250.f);
    }
}