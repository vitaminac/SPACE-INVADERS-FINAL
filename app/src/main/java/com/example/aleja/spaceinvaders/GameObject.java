package com.example.aleja.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface GameObject {

    /**
     * @param bodies the different rigidbodies that in the world
     * @return the rigid body that will collide with
     */
    // GameObject willCollide(GameObject[] bodies);

    /**
     * conditionally draw the object base on context
     */
    void draw(Canvas canvas, Paint paint);

    /**
     * @param x coordinate x of window where the touch happen
     * @param y coordinate y of window where the touch happen
     */
    void onTouchDown(float x, float y);

    void onTouchUp(float x, float y);
}
