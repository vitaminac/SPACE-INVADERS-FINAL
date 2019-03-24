package com.example.aleja.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class BotonM implements TouchableGameObject {
    RectF rect;

    private float length;
    private float height;

    private float x;
    private float y;
    
    private final int direction;
    private Nave nave;
    
    private Bitmap bitmap;

    public BotonM(Context context, int screenX, int screenY, float pX, float pY, int direction, Nave nave, Bitmap bitmap){
        rect = new RectF();

        length = screenX/30;
        height = screenY/25;

        x = screenX - pX;
        y = screenY - pY;

        this.bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);
        
        this.direction = direction;
        this.nave = nave;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.bitmap, this.getX(), this.getY(), paint);
    }


    @Override
    public void onTouchDown(float x, float y) {
        if ((x > this.getX()) && (x < this.getX() + this.getLength())
                && (y > this.getY()) && (y < this.getY() + this.getHeight())) {
            this.nave.setMovementState(this.direction);
        }
    }

    @Override
    public void onTouchUp(float x, float y) {
        // disable
    }
}
