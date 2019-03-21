package com.example.aleja.spaceinvaders;

public interface Touchable {
    /**
     * @param x coordinate x of window where the touch happen
     * @param y coordinate y of window where the touch happen
     */
    void onTouchDown(float x, float y);

    void onTouchUp(float x, float y);
}
