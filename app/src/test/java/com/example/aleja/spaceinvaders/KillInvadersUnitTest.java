package com.example.aleja.spaceinvaders;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KillInvadersUnitTest {
    @Test
    public void kill_invaders() {
        final VistaSpaceInvaders invaders = mock(VistaSpaceInvaders.class);
        final Marcianito marcianito = mock(Marcianito.class);
        when(marcianito.getX()).thenReturn(250.0f);
        when(marcianito.getY()).thenReturn(250.0f);
        Marcianito[] marcianitos = new Marcianito[]{marcianito};
        when(invaders.getNumMarcianitos()).thenReturn(marcianitos.length);
        doCallRealMethod().when(marcianito).setInvisible();
        when(invaders.getMarcianito()).thenReturn(marcianitos);
        doCallRealMethod().when(invaders).killWithTheFinger(anyFloat(), anyFloat());
        invaders.killWithTheFinger(250f, 250f);
        verify(marcianito).setInvisible();
    }
}