package com.example.aleja.spaceinvaders.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.MotionEvents;
import android.view.MotionEvent;
import android.view.View;
import com.example.aleja.spaceinvaders.Marcianito;
import com.example.aleja.spaceinvaders.VistaSpaceInvaders;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.assertFalse;

public class KillWithTheFingerSteps {
    public ViewAction touchDownAndUp(final float x, final float y) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "Send touch events.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                // Get view absolute position
                int[] location = new int[2];
                view.getLocationOnScreen(location);

                // Offset coordinates by view position
                float[] coordinates = new float[]{x + location[0], y + location[1]};
                float[] precision = new float[]{1f, 1f};

                // Send down event, pause, and send up
                MotionEvent down = MotionEvents.sendDown(uiController, coordinates, precision).down;
                uiController.loopMainThreadForAtLeast(200);
                MotionEvents.sendUp(uiController, down, coordinates);
            }
        };
    }

    static Marcianito marcianito;

    static Context appContext;
    static VistaSpaceInvaders vista;

    @Given("^There is a running game$")
    public void there_is_a_running_game() throws Throwable {
        appContext = InstrumentationRegistry.getTargetContext();
        vista = new VistaSpaceInvaders(appContext, 800, 600, true, "Hola", true);
    }

    @And("^There is a invader$")
    public void there_is_a_invader() {
        marcianito = vista.getMarcianito()[0];
    }

    @When("^I touch a invader with the finger$")
    public void i_touch_a_invader_with_finger() {
        vista.onTouchDown(marcianito.getX(), marcianito.getY());
    }

    @Then("^The invader dies$")
    public void the_invader_dies() {
        assertFalse(marcianito.isVisible());
    }
}
