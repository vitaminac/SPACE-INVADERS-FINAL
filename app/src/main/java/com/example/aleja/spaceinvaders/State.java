package com.example.aleja.spaceinvaders;

public class State {
    public static volatile int puntuacion = 0;

    // Selector de bitmap
    public static final int Red = 1;

    public static int selectColor = Red;

    public static synchronized void changeColor() {
        selectColor = 1 - selectColor;
    }
}
