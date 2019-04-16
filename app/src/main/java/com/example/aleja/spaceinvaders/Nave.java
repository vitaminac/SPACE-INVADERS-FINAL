package com.example.aleja.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import static com.example.aleja.spaceinvaders.State.Red;
import static com.example.aleja.spaceinvaders.State.selectColor;

public class Nave implements TouchableGameObject {
    RectF rect;

    // La nave espacial del jugador será representada por un Bitmap
    private Bitmap bitmap;

    // Que tan ancho y alto puede llegar nuestra nave espacial
    private float length;
    private float height;

    // X es la parte extrema a la izquierda del rectángulo el cual forma nuestra nave espacial
    private float x;

    // Y es la coordenada de a mero arriba
    private float y;

    // Esto va a mantener la rapidez de los pixeles por segundo a la que la nave espacial se moverá
    private float velocidadNav;

    // La nave espacial del jugador va a ser representada por un Bitmap
    private static Bitmap bitmap1 = null;
    private static Bitmap bitmap2 = null;

    // En qué direcciones se puede mover la nave espacial
    public final int PARADA = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int UP = 3;
    public final int DOWN = 4;

    // Se esta moviendo la nave espacial y en que dirección
    private int shipMoving = PARADA;

    private int ejeX;
    
    // Este es el método del constructor
    // Cuando creamos un objeto de esta clase daremos
    // la anchura y la altura de la pantalla
    public Nave(Context context, int screenX, int screenY){

        // Inicializa un RectF vacío
        rect = new RectF();

        length = screenX/17;
        height = screenY/10;

        // Inicia la nave en el centro de la pantalla aproximadamente
        x = screenX / 2;
        y = screenY - height - 10;

        // arreglo de problema de memoria
        // Inicializa el bitmap
        if (bitmap1 == null) {
            // Ajusta el bitmap a un tamaño proporcionado a la resolución de la pantalla
            bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nave1);
            bitmap1 = Bitmap.createScaledBitmap(bitmap1,
                    (int) (length),
                    (int) (height),
                    false);
        }
        if (bitmap2 == null) {
            // Ajusta el bitmap a un tamaño proporcionado a la resolución de la pantalla
            bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.nave2);
            bitmap2 = Bitmap.createScaledBitmap(bitmap2,
                    (int) (length),
                    (int) (height),
                    false);
        }

        // Qué tan rápido va la nave espacial en pixeles por segundo
        velocidadNav = 350;
        
        this.ejeX = screenX;
    }

    public RectF getRect(){
        return rect;
    }



    public Bitmap getBitmap(){
        if (selectColor == Red) {
            return bitmap1;
        } else {
            return bitmap2;
        }
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getLength(){
        return length;
    }
    
    public float getHeight() {
        return height;
    }

    // Este método será usado para cambiar/establecer si la nave
    // espacial va a la izquierda, la derecha o no se mueve
    public void setMovementState(int state){
        shipMoving = state;
    }

    // Este método de update será llamado desde el update en SpaceInvadersView
    // Determina si la nave espacial del jugador necesita moverse y cambiar las coordenadas
    // que están en x si es necesario
    public void update(long fps, boolean tocaD, boolean tocaI, boolean tocaAR,boolean tocaAB){


            if ((shipMoving == LEFT)&&(!tocaI)) {
                x = x - velocidadNav / fps;
            }

            if ((shipMoving == RIGHT)&&(!tocaD)) {
                x = x + velocidadNav / fps;
            }

            if ((shipMoving == UP)&&(!tocaAR)) {
                y = y - velocidadNav / fps;
            }

            if ((shipMoving == DOWN)&&(!tocaAB)) {
                y = y + velocidadNav / fps;
            }

            // Actualiza rect el cual es usado para detectar impactos
            rect.top = y;
            rect.bottom = y + height;
            rect.left = x;
            rect.right = x + length;

    }

    public void update(long fps){
        // Actualiza rect el cual es usado para detectar impactos
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;

    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(this.getBitmap(), this.getX(), this.getY(), paint);
    }

    @Override
    public void onTouchUp(float x, float y) {
        if ((x < ejeX / 2)) {
            this.setMovementState(this.PARADA);
        }
    }

    @Override
    public void onTouchDown(float x, float y) {
        // disable
    }
}
