package com.example.aleja.spaceinvaders;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Bloque implements GameObject {
    private RectF rect;

    private boolean isVisible;

    public Bloque(int row, int column, int shelterNumber, int screenX, int screenY) {

        int width = screenX / 90;
        int height = screenY / 40;

        isVisible = true;

        int brickPadding = 1;

        // El número de guaridas
        int shelterPadding = screenX / 9;
        int startHeight = (int) (screenY - (screenY / 8 * 2.2));

        rect = new RectF(column * width + brickPadding +
                (shelterPadding * shelterNumber) +
                shelterPadding + shelterPadding * shelterNumber,
                row * height + brickPadding + startHeight,
                column * width + width - brickPadding +
                        (shelterPadding * shelterNumber) +
                        shelterPadding + shelterPadding * shelterNumber,
                row * height + height - brickPadding + startHeight);
    }

    public RectF getRect() {
        return this.rect;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if (this.getVisibility()) {
            canvas.drawRect(this.getRect(), paint);
        }
    }

    @Override
    public void onTouchDown(float x, float y) {
        
    }

    @Override
    public void onTouchUp(float x, float y) {

    }
}
