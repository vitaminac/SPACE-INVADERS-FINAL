package com.example.aleja.spaceinvaders;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KillInvadersUnitTest {
    private VistaSpaceInvaders invaders;
    private Marcianito marcianito;

    @Given("^There is a running game$")
    public void there_is_a_running_game() throws Throwable {
        invaders = mock(VistaSpaceInvaders.class);
        doCallRealMethod().when(invaders).onTouchDown(anyFloat(), anyFloat());
        when(invaders.getTouchableGameObjects()).thenAnswer(new Answer<List<Marcianito>>() {
            @Override
            public List<Marcianito> answer(InvocationOnMock invocation) {
                return Collections.singletonList(marcianito);
            }
        });
    }

    @And("^There is at least a invader$")
    public void there_is_at_least_a_invader() {
        marcianito = mock(Marcianito.class);
        when(marcianito.getX()).thenReturn(250.0f);
        when(marcianito.getY()).thenReturn(250.0f);
        doCallRealMethod().when(marcianito).onTouchDown(anyFloat(), anyFloat());
    }

    @When("^I touch a invader with the finger$")
    public void i_touch_a_invader_with_the_finger() {
        invaders.onTouchDown(250.f, 250.f);
    }

    @Then("^The invader dies quickly$")
    public void the_invader_dies() {
        verify(marcianito).setInvisible();
    }
}