package com.example.aleja.spaceinvaders;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 600; j++) {
                vista.onTouchDown(i, j);
            }
        }
        final List<GameObject> gameObjects = vista.getGameObjects();
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i).getClass().equals(Marcianito.class)) {
                assertFalse(((Marcianito) gameObjects.get(i)).isVisible());
            }
        }
    }
}
