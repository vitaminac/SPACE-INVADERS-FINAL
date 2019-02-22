package com.example.aleja.spaceinvaders;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class KillInvadersTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        final VistaSpaceInvaders vista = new VistaSpaceInvaders(appContext, 800, 600, true, "Hola", true);
        // vista.killWithTheFinger(10, 10);
        final Marcianito[] marcianito = vista.getMarcianito();
        for (int i = 0; i < vista.getNumMarcianitos(); i++) {
            vista.killWithTheFinger(marcianito[i].getX(), marcianito[i].getY());
            assertFalse(marcianito[i].isVisible());
        }
    }
}
