package com.example.aleja.spaceinvaders.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.MotionEvents;
import android.view.MotionEvent;
import android.view.View;
import com.example.aleja.spaceinvaders.Laser;
import com.example.aleja.spaceinvaders.Marcianito;
import com.example.aleja.spaceinvaders.Nave;
import com.example.aleja.spaceinvaders.VistaSpaceInvaders;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class StepsDef {
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

    static Context appContext;
    static VistaSpaceInvaders vista;
    static Laser laser;
    static Marcianito marcianito;

    @Given("^There is a running game$")
    public void there_is_a_running_game() throws Throwable {
        appContext = InstrumentationRegistry.getTargetContext();
        vista = new VistaSpaceInvaders(appContext, 800, 600, true, "Hola", true);
    }

    @And("^There is a bullet shoot by a spaceship$")
    public void there_is_a_bullet_shoot_by_a_spaceship() {
        laser = vista.getLaser();
    }

    @And("^There is a invader$")
    public void there_is_a_invader() {
        marcianito = vista.getMarcianito()[0];
    }

    @When("^The bullet hit a invader$")
    public void the_bullet_hit_a_invader() {
        laser.shoot(marcianito.getX(), marcianito.getY(), Nave.UP);
        laser.update(100);
        vista.update();
    }
}
