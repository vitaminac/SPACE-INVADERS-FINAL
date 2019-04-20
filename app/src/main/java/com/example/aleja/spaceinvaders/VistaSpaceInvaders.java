package com.example.aleja.spaceinvaders;

import static com.example.aleja.spaceinvaders.State.puntuacion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class VistaSpaceInvaders extends SurfaceView implements Runnable {
    Context context;

    Random generator = new Random();

    MediaPlayer mediaPlayer;

    private int hack;

    private boolean tocaD, tocaI, tocaAR, tocaAB;

    private boolean predict1, predict2, predict3;

    // Esta es nuestra secuencia
    private Thread hiloJuego = null;

    // Nuestro SurfaceHolder para bloquear la superficie antes de que dibujemos nuestros gráficos
    private SurfaceHolder ourHolder;

    // Un booleano el cual vamos a activar y desactivar
    // cuando el juego este activo- o no.
    private volatile boolean jugando;

    // El juego esta pausado al iniciar
    private boolean pausado = true;

    // Un objeto de lienzo (Canvas) y de pintar (Paint)
    private Canvas canvas;
    private Paint paint;

    // Esta variable rastrea los cuadros por segundo del juego
    private long fps;

    // Esto se utiliza para ayudar a calcular los cuadros por segundo
    private long timeThisFrame;

    // El tamaño de la pantalla en pixeles
    private int ejeX;
    private int ejeY;

    // La nave del jugador
    private Nave nave;
    // Nave de ayuda
    private Nave esparrin;

    // Laser
    private Laser laser;

    // Laser de invader espotaneo
    private Laser espLaser;

    // laseres de los marcianitos
    private Laser[] marcianitoLaser = new Laser[200];
    private int proxLaser;
    private int maxMarcianitosLaser = 10;

    // Hasta 60 Marcianitos
    Marcianito[] marcianito = new Marcianito[60];
    int numMarcianitos = 0;

    // Marciano espontaneo
    private Marcianito marcianitoEsp;

    // Las guaridas del jugador están construidas a base de ladrillos
    private Bloque[] bloques;
    private int numBloque;

    // Vidas
    private int vidas = 1;

    // flag que indica si habilita el disparo
    private boolean isAdult;

    // flag que indica si habilita el rebote
    private boolean rebotes;

    // nombre de jugador
    private String name;

    private List<GameObject> gameObjects = new ArrayList<>();

    // Cuando inicializamos (call new()) en gameView
    // Este método especial de constructor se ejecuta
    public VistaSpaceInvaders(Context context, int x, int y, boolean isAdult, String name, boolean rebotes) {
        // La siguiente línea del código le pide a
        // la clase de SurfaceView que prepare nuestro objeto.
        // !Que amable¡.
        super(context);

        this.isAdult = isAdult;
        this.name = name;
        this.rebotes = rebotes;

        // Hace una copia del "context" disponible globalmete para que la usemos en otro método
        this.context = context;

        // Inicializa los objetos de ourHolder y paint
        ourHolder = getHolder();
        paint = new Paint();

        ejeX = x;
        ejeY = y;

        prepararNivel();
    }

    // Aquí vamos a inicializar todos los objetos del juego
    private void prepararNivel() {

        //Iniciar musica
        mediaPlayer = MediaPlayer.create(context, R.raw.musica1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //Inicializar hack a 0
        hack = 0;

        // Haz una nave espacial para un jugador nuevo
        nave = new Nave(context, ejeX, ejeY);
        this.gameObjects.add(nave);
        // Inicializa el esparril
        esparrin = new Nave(context, ejeX, ejeY);

        // Prepara la bala del jugador
        laser = new Laser(ejeY);
        this.gameObjects.add(laser);

        // Prepara la bala del espontaneo
        espLaser = new Laser(ejeY);
        this.gameObjects.add(this.espLaser);

        // Prepara botones de disparo
        BotonM BArriba = new BotonM(context, ejeX, ejeY, 1700, 150, nave.UP, nave, BitmapFactory.decodeResource(context.getResources(), R.drawable.botonarriba));
        this.gameObjects.add(BArriba);
        BotonM BAbajo = new BotonM(context, ejeX, ejeY, 1700, 50, nave.DOWN, nave, BitmapFactory.decodeResource(context.getResources(), R.drawable.botonabajo));
        this.gameObjects.add(BAbajo);
        BotonM BDerecha = new BotonM(context, ejeX, ejeY, 1650, 100, nave.RIGHT, nave, BitmapFactory.decodeResource(context.getResources(), R.drawable.botonderecha));
        this.gameObjects.add(BDerecha);
        BotonM BIzquierda = new BotonM(context, ejeX, ejeY, 1750, 100, nave.LEFT, nave, BitmapFactory.decodeResource(context.getResources(), R.drawable.botonizquierda));
        this.gameObjects.add(BIzquierda);

        // Inicializa la formación de invadersBullets
        for (int i = 0; i < marcianitoLaser.length; i++) {
            marcianitoLaser[i] = new Laser(ejeY);
            this.gameObjects.add(marcianitoLaser[i]);
        }

        // Construye un ejercito de invaders
        numMarcianitos = 0;
        for (int column = 0; column < 6; column++) {
            for (int row = 1; row < 5; row++) {
                marcianito[numMarcianitos] = new Marcianito(context, row, column, ejeX, ejeY);
                this.gameObjects.add(marcianito[numMarcianitos]);
                numMarcianitos++;
            }
        }

        // Construye el invader espontaneo
        marcianitoEsp = new Marcianito(context, ejeX, ejeY);
        this.gameObjects.add(marcianitoEsp);

        // Construye las guaridas
        numBloque = 0;
        bloques = new Bloque[4 * 9 * 4];
        for (int shelterNumber = 0; shelterNumber < 4; shelterNumber++) {
            for (int column = 0; column < 9; column++) {
                for (int row = 0; row < 4; row++) {
                    bloques[numBloque] = new Bloque(row, column, shelterNumber, ejeX, ejeY);
                    this.gameObjects.add(bloques[numBloque]);
                    numBloque++;
                }
            }
        }
    }

    @Override
    public void run() {
        while (jugando) {

            // Captura el tiempo actual en milisegundos en startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Actualiza el cuadro
            if (!pausado) {
                update();
            }

            // Dibuja el cuadro
            dibujar();

            // Calcula los cuadros por segundo de este cuadro
            // Ahora podemos usar los resultados para
            // medir el tiempo de animaciones y otras cosas más.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

        }


    }

    private void dibujar() {

        if (ourHolder.getSurface().isValid()) {


            // Bloquea el lienzo para que este listo para dibujar
            canvas = ourHolder.lockCanvas();

            // Dibuja el color del fondo
            canvas.drawColor(Color.argb(255, 0, 0, 0)/*, PorterDuff.Mode.CLEAR*/);
            // Escoje el color de la brocha para dibujar
            paint.setColor(Color.argb(255, 255, 255, 255));

            for (GameObject body : this.gameObjects) {
                body.draw(canvas, paint);
            }

            // Dibuja la puntuación y las vidas restantes
            // Cambia el color de la brocha
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Puntuacion: " + puntuacion + " Vidas: " + vidas, 10, 50, paint);

            // Extrae todo a la pantalla
            ourHolder.unlockCanvasAndPost(canvas);
        }


    }

    private void update() {
        Integer contadorMuertos = 0;

        // Check if win or lose
        if (numMarcianitos * 100 == puntuacion)
            win();
        else if (vidas == 0)
            lose();

        // ¿Chocó el invader contra el lado de la pantalla?
        boolean bumped = false;

        // ¿Ha perdido el jugador?
        boolean pierde = false;

        // Mueve la nave espacial del jugador

        tocaD = false;
        tocaI = false;
        tocaAB = false;
        tocaAR = false;


        if (nave.getX() > ejeX - nave.getLength()) {
            tocaD = true;
        }

        if (nave.getX() < 0) {
            tocaI = true;
        }

        if (nave.getY() < 0) {
            tocaAR = true;
        }

        if (nave.getY() > ejeY - nave.getHeight()) {
            tocaAB = true;
        }

        nave.update(fps, tocaD, tocaI, tocaAR, tocaAB);

        marcianitoEsp.update(fps);

        if (marcianitoEsp.getX() > ejeX - marcianitoEsp.getLength()) {
            marcianitoEsp.reinicio();
        }

        if (marcianitoEsp.getVisibility()) {
            if (marcianitoEsp.takeAimEsp(nave.getX(), nave.getLength())) {
                espLaser.shoot(marcianitoEsp.getX() + marcianitoEsp.getLength() / 2,
                        marcianitoEsp.getY(), laser.ABAJO);
            }
        }

        // Actualiza a todos los invaders si están visibles
        for (int i = 0; i < numMarcianitos; i++) {

            if (marcianito[i].getVisibility()) {
                // Mueve el siguiente invader
                marcianito[i].update(fps);

                // ¿Quiere hacer un disparo?
                if (marcianito[i].takeAim(nave.getX(),
                        nave.getLength())) {

                    // Si sí, intentalo y genera una bala
                    if (marcianitoLaser[proxLaser].shoot(marcianito[i].getX()
                                    + marcianito[i].getLength() / 2,
                            marcianito[i].getY(), laser.ABAJO)) {

                        // Disparo realizado
                        // Preparete para el siguiente disparo
                        proxLaser++;

                        // Inicia el ciclo repetitivo otra vez al
                        // primero si ya hemos llegado al último.
                        if (proxLaser == maxMarcianitosLaser) {
                            // Esto detiene el disparar otra bala hasta
                            // que una haya completado su trayecto.
                            // Por que si bullet 0 todavia está activo,
                            // shoot regresa a false.
                            proxLaser = 0;
                        }
                    }
                }

                // Si ese movimiento causó que golpearan la pantalla,
                // cambia bumped a true.
                if (marcianito[i].getX() > ejeX - marcianito[i].getLength()
                        || marcianito[i].getX() < 0) {

                    bumped = true;

                }
            }// suma puntuacion
            else {
                contadorMuertos++;
            }
        }

        puntuacion = contadorMuertos * 100;

        // ¿Chocó algún invader en el extremo de la pantalla?

        if (bumped) {
            // Mueve a todos los invaders hacia abajo y cambia la dirección
            for (int i = 0; i < numMarcianitos; i++) {
                marcianito[i].dropDownAndReverse();
                // Han aterrizado los invaders
                if (marcianito[i].getVisibility()) {
                    if (marcianito[i].getY() > ejeY - marcianito[i].getHeight()) {
                        pierde = true;
                    }
                }
            }
        }

        // Desaparicion y aparicion aleatoria de nave
        int randomN = generator.nextInt(200);
        if (randomN == 1) {
            predict1 = false;
            predict2 = false;
            predict3 = false;
            int newX = generator.nextInt(ejeX);
            int newY = generator.nextInt(ejeY);
            esparrin.setX(newX);
            esparrin.setY(newY);
            esparrin.update(fps);

            for (int i = 0; i < numMarcianitos; i++) {
                if (marcianito[i].getVisibility()) {
                    if ((RectF.intersects(marcianito[i].getRect(), esparrin.getRect()))) {
                        predict1 = true;
                    }
                }
            }

            for (int j = 0; j < numBloque; j++) {
                if (bloques[j].getVisibility()) {
                    if ((RectF.intersects(esparrin.getRect(), bloques[j].getRect()))) {
                        predict2 = true;
                    }
                }
            }

            if (marcianitoEsp.getVisibility()) {
                if (RectF.intersects(marcianitoEsp.getRect(), esparrin.getRect())) {
                    predict3 = true;
                }
            }

            if (!((predict1) || (predict2) || (predict3))) {
                nave.setX(newX);
                nave.setY(newY);
            }
        }

        // Ha impactado la nave con la barrera
        for (int i = 0; i < numBloque; i++) {
            if (bloques[i].getVisibility()) {
                if (RectF.intersects(nave.getRect(), bloques[i].getRect())) {
                    pierde = true;
                }
            }
        }

        // Ha impactado un marciano con la barrera
        for (int i = 0; i < numMarcianitos; i++) {
            if (marcianito[i].getVisibility()) {
                for (int j = 0; j < numBloque; j++) {
                    if (bloques[j].getVisibility()) {
                        if (RectF.intersects(marcianito[i].getRect(), bloques[j].getRect())) {
                            bloques[j].setInvisible();
                        }
                    }
                }
            }
        }

        // Ha impactado un invader con la nave
        for (int i = 0; i < numMarcianitos; i++) {
            if (marcianito[i].getVisibility()) {
                if (RectF.intersects(marcianito[i].getRect(), nave.getRect())) {
                    pierde = true;
                }
            }
        }

        // Ha impactado el invader espontaneo con la nave
        if (marcianitoEsp.getVisibility()) {
            if (RectF.intersects(marcianitoEsp.getRect(), nave.getRect())) {
                pierde = true;
            }
        }

        if (pierde) {
            lose();
        }

        if (hack == 3) {
            win();
        }

        if (this.isAdult) {

            // Actualiza la bala del jugador
            if (laser.getStatus()) {
                laser.update(fps);
            }

            // Actualiza la bala del espontaneo
            if (espLaser.getStatus()) {
                espLaser.update(fps);
            }

            // Actualiza todas las balas de los invaders si están activas
            for (int i = 0; i < marcianitoLaser.length; i++) {
                if (marcianitoLaser[i].getStatus()) {
                    marcianitoLaser[i].update(fps);
                }
            }

            if (this.rebotes) {
                // Ha tocado la parte alta de la pantalla la bala del jugador
                if (laser.getImpactPointY() < 0) {
                    laser.changeDir();
                } else if (laser.getImpactPointY() > ejeY) {
                    laser.changeDir();
                }

                // Ha tocado la parte baja de la pantalla la bala del invader espontaneo
                if (espLaser.getImpactPointY() > ejeY) {
                    espLaser.changeDir();
                    if (!(espLaser.isLetal())) {
                        espLaser.hacerLetal();
                    }
                } else if (espLaser.getImpactPointY() < 0) {
                    espLaser.changeDir();
                }

                // Ha tocado la parte baja de la pantalla la bala del invader
                for (int i = 0; i < marcianitoLaser.length; i++) {
                    if (marcianitoLaser[i].getImpactPointY() > ejeY) {
                        marcianitoLaser[i].changeDir();
                        if (!(marcianitoLaser[i].isLetal())) {
                            marcianitoLaser[i].hacerLetal();
                        }
                    } else if (marcianitoLaser[i].getImpactPointY() < 0) {
                        marcianitoLaser[i].changeDir();
                    }
                }

                // Si la bala ha tocado suelo es letal para los invader
                for (int i = 0; i < marcianitoLaser.length; i++) {
                    if ((marcianitoLaser[i].isLetal()) && (marcianitoLaser[i].getStatus())) {
                        for (int j = 0; j < numMarcianitos; j++) {
                            marcianito[j].onCollide(marcianitoLaser[i]);
                        }
                        marcianitoEsp.onCollide(marcianitoLaser[i]);
                    }
                }

                if ((espLaser.isLetal()) && (espLaser.getStatus())) {
                    for (int i = 0; i < numMarcianitos; i++) {
                        if (marcianito[i].getVisibility()) {
                            if (RectF.intersects(espLaser.getRect(), marcianito[i].getRect())) {
                                espLaser.setInactive();
                                marcianito[i].setInvisible();
                                puntuacion = puntuacion + 100;
                                // Ha ganado el jugador
                            }
                            if (RectF.intersects(espLaser.getRect(), marcianitoEsp.getRect())) {
                                espLaser.setInactive();
                                marcianitoEsp.setInvisible();
                            }
                        }
                    }
                }

            } else {
                // Ha tocado la parte alta de la pantalla la bala del jugador
                if (laser.getImpactPointY() < 0) {
                    laser.setInactive();
                }

                // Ha tocado la parte baja de la pantalla la bala del invader
                for (int i = 0; i < marcianitoLaser.length; i++) {
                    if (marcianitoLaser[i].getImpactPointY() > ejeY) {
                        marcianitoLaser[i].setInactive();
                    }
                }

                // Ha tocado la parte baja de la pantalla la bala del invader espontaneo
                if (espLaser.getImpactPointY() > ejeY) {
                    espLaser.setInactive();
                }
            }

            // Ha tocado la bala del jugador a algún invader
            if (laser.getStatus()) {
                for (int i = 0; i < numMarcianitos; i++) {
                    if (marcianito[i].getVisibility()) {
                        if (RectF.intersects(laser.getRect(), marcianito[i].getRect())) {
                            marcianito[i].setInvisible();
                            laser.setInactive();
                            puntuacion = puntuacion + 100;

                            // Ha ganado el jugador
                        }
                    }
                }
            }

            // Ha tocado la bala del jugador al invader espontaneo
            if (laser.getStatus()) {
                if (marcianitoEsp.getVisibility()) {
                    if (RectF.intersects(laser.getRect(), marcianitoEsp.getRect())) {
                        marcianitoEsp.setInvisible();
                        laser.setInactive();
                    }
                }
            }

            // Ha impactado una bala alienígena a un ladrillo de la guarida
            for (int i = 0; i < marcianitoLaser.length; i++) {
                this.laserMayHitWithBlock(marcianitoLaser[i]);
            }
            // Ha impactado una bala alienígena esponataneo a un ladrillo de la guarida
            this.laserMayHitWithBlock(espLaser);
            // Ha impactado una bala del jugador a un ladrillo de la guarida
            this.laserMayHitWithBlock(laser);

            // Ha impactado una bala de un invader a la nave espacial del jugador
            for (int i = 0; i < marcianitoLaser.length; i++) {
                if (marcianitoLaser[i].getStatus()) {
                    if (RectF.intersects(nave.getRect(), marcianitoLaser[i].getRect())) {
                        marcianitoLaser[i].setInactive();
                        vidas--;
                    }
                }
            }

            // Ha impactado una bala del invader espontaneo a la nave espacial del jugador
            if (espLaser.getStatus()) {
                if (RectF.intersects(nave.getRect(), espLaser.getRect())) {
                    espLaser.setInactive();
                    vidas--;
                }
            }

        }
    }

    public void laserMayHitWithBlock(Laser laser) {
        for (Bloque bloque : this.getBloques()) {
            bloque.onCollide(laser);
        }
    }

    public Bloque[] getBloques() {
        return bloques;
    }

    // Si SpaceInvadersActivity es pausado/detenido
    // apaga nuestra secuencia.
    public void pause() {
        jugando = false;
        try {
            hiloJuego.join();
        } catch (InterruptedException e) {
            Thread.interrupted();
            Log.e("Error:", "joining thread");
        }

    }

    // Si SpaceInvadersActivity es iniciado entonces
    // inicia nuestra secuencia.
    public void resume() {
        jugando = true;
        hiloJuego = new Thread(this);
        hiloJuego.start();
    }

    private void win() {
        mediaPlayer.pause();
        final Activity activity = (Activity) getContext();
        Intent intent = new Intent(activity, MenuActivity.class);
        intent.putExtra(getResources().getString(R.string.name), this.name);
        intent.putExtra(getResources().getString(R.string.victory), true);
        intent.putExtra(getResources().getString(R.string.score), puntuacion);
        intent.putExtra("adult", isAdult);
        intent.putExtra("rebote", rebotes);
        activity.finish();
        activity.startActivity(intent);
        Thread.currentThread().interrupt();
    }

    private void lose() {
        mediaPlayer.pause();
        final Activity activity = (Activity) getContext();
        Intent intent = new Intent(activity, MenuActivity.class);
        intent.putExtra(getResources().getString(R.string.name), this.name);
        intent.putExtra(getResources().getString(R.string.victory), false);
        intent.putExtra(getResources().getString(R.string.score), puntuacion);
        intent.putExtra("adult", isAdult);
        intent.putExtra("rebote", rebotes);
        activity.finish();
        activity.startActivity(intent);
        Thread.currentThread().interrupt();
    }

    // La clase de SurfaceView implementa a onTouchListener
    // Así es que podemos anular este método y detectar toques a la pantalla.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float toucheventX = motionEvent.getX();
        float toucheventY = motionEvent.getY();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // El jugador ha tocado la pantalla
            case MotionEvent.ACTION_DOWN:
                pausado = false;
                if ((motionEvent.getY() < ejeY) && (motionEvent.getX() > ejeX / 2)) {
                    // Disparos lanzados	
                    if (laser.shoot(nave.getX() + nave.getLength() / 2, nave.getY(), laser.ARRIBA)) {
                    }

                }
                // Tocar marciano espontaneo tres veces para hack
                if ((toucheventX > marcianitoEsp.getX()) && (toucheventX < marcianitoEsp.getX() + marcianitoEsp.getLength()) &&
                        (toucheventY > marcianitoEsp.getY()) && (toucheventY < marcianitoEsp.getY() + marcianitoEsp.getHeight())) {
                    hack++;
                }

                this.onTouchDown(toucheventX, toucheventY);
                break;

            // El jugador ha retirado su dedo de la pantalla
            case MotionEvent.ACTION_UP:
                nave.onTouchUp(toucheventX, toucheventY);

                break;
        }
        return true;
    }

    public List<TouchableGameObject> getTouchableGameObjects() {
        return this.gameObjects.stream().filter(o -> o instanceof TouchableGameObject).map(o -> (TouchableGameObject) o).collect(Collectors.toList());
    }

    public void onTouchDown(float x, float y) {
        for (TouchableGameObject object : this.getTouchableGameObjects()) {
            object.onTouchDown(x, y);
        }
    }

    public List<GameObject> getGameObjects() {
        return this.gameObjects;
    }

    public Marcianito[] getMarcianito() {
        return marcianito;
    }
}
