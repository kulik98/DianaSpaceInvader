package com.example.dianaspaceinvader;

import static com.example.dianaspaceinvader.Images.bulletSpaceship;
import static com.example.dianaspaceinvader.Images.invadersBitmap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {


    private final Object LOCK = new Object();
    public static final long FPS = 60; /* frames per second */

    private Spaceship spaceship;

    private Bitmap spaceBackground;

    private int spaceBackgroundY;

    private int screenHeight;
    private int screenWidth;
    private int newScreenHeight;
    public static int newScreenWidth;


    private boolean mRunning = false;
    private Thread gameThread = null;

    private Context context;
    private SurfaceHolder surfaceHolder;
    Canvas canvas;

    private List<SpaceshipBullet> spaceshipBullets;
    private List<InvaderBullet> invaderBullets;
    private List<Invader> invaders;

    private Bitmap bitmapForBullet;

    private int level;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;


    public GameView(Context context) {
        super(context);
        init(context);

        spaceshipBullets = new ArrayList<SpaceshipBullet>();
        invaderBullets = new ArrayList<InvaderBullet>();
        invaders = new ArrayList<Invader>();
        spaceBackground = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.spacebackgroundfinished);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Log.d("banana", "surface view size :" + size.x + "," + size.y);

        SCREEN_HEIGHT = size.y;
        SCREEN_WIDTH = size.x;


        screenWidth = size.x;//גודל המסך רוחב
        screenHeight = size.y;//גודל המסך אורך
        float backgroundHeight = spaceBackground.getHeight();//גודל אורך התמונה
        float backgroundWidth = spaceBackground.getWidth();//גודל רוחב התמונה

        float ratio = backgroundWidth / backgroundHeight;//יחס רוחב התמונה לאורך שלה

        newScreenHeight = screenHeight;
        newScreenWidth = (int) (ratio * screenHeight);//גודל אורך המסך כפול גודל רוחב התמונה חלקי גודל אורך התמונה
        spaceBackground = Bitmap.createScaledBitmap(spaceBackground, newScreenWidth, newScreenHeight, false);

        spaceship = new Spaceship(screenWidth, screenHeight);


    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context mcontext) {
        context = mcontext;
        surfaceHolder = getHolder();
    }


    @Override
    public void run() {

        int countSpaceship = 0;
        int countInvader = 0;
        int currentTime = 0;
        int invaderBulletInterval = (int) ((int)Math.random() * (FPS*2- 1*FPS + 1) + 1*FPS);




        while (mRunning) {

            countSpaceship++;
            countInvader++;
            currentTime++;

            synchronized (LOCK) {
                if (currentTime == invaderBulletInterval) {
                    invaderBulletInterval = (int) ((int) Math.random() * (FPS * 2 - 1 * FPS + 1) + 1 * FPS);
                    int randomInvaderShooter = (int) Math.random() * (invaders.size() - 0 + 1) + 0;
                    if(invaders.size()!=0)
                    {
                        int posX = invaders.get(randomInvaderShooter).getX() + (int) 0.5 * invadersBitmap.get(0).getWidth();
                        int posY = invaders.get(randomInvaderShooter).getY() + (int) invadersBitmap.get(0).getHeight();
                        addInvaderBullet(posX, posY);
                        currentTime = 0;
                    }



                }
            }






            synchronized (LOCK) {

                List<SpaceshipBullet> tempSpaceshipBullets = new ArrayList<SpaceshipBullet>();

                for (SpaceshipBullet b : spaceshipBullets) {


                    b.update();
                    isInvaderDamaged();

                    if (!b.isScreenBounds()) {
                        tempSpaceshipBullets.add(b);

                    }

                }
                spaceshipBullets = tempSpaceshipBullets;

            }


            synchronized (LOCK) {

                List<InvaderBullet> tempInvadersBullets = new ArrayList<>();

                for (InvaderBullet b : invaderBullets) {
                    b.update();


                    if (b.isScreenBounds()) {
                        tempInvadersBullets.add(b);
                    }
                }
                invaderBullets = tempInvadersBullets;

            }



            synchronized (LOCK) {
                List<Invader> tempInvaders = new ArrayList<Invader>();
                for (Invader invader : invaders) {

                    invader.update();
                    isInvaderDamaged();

                    if (invader.isVisible()) {
                        if(isInvaderDamaged())
                        {
                            Log.d("peach", "isDamaged:" + isInvaderDamaged());
                        }
                        else{
                            tempInvaders.add(invader);
                            Log.d("peach", "isDamaged:" + isInvaderDamaged());
                        }


                    }
                }
                invaders = tempInvaders;
            }


            if (countSpaceship == FPS / 4) {
                for (SpaceshipBullet b : spaceshipBullets) {
                    b.changeBitmap();
                }
                for (Invader invader : invaders) {
                    invader.changeBitmap();
                }

                spaceship.changeBitmap();

                countSpaceship = 0;

            }



            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();


                spaceBackgroundY -= 4;//כל פעם מזיז התמונה ב4 יחידות לאורך הציר האנכי כלומר מטה למעלה

                if (spaceBackgroundY < -newScreenHeight)//כאשר קורדינאטת הY של התמונה יותר גדולה  מגודל המסך אז מאפסים את מיקומה כלומר למעלה
                {
                    spaceBackgroundY = 0;
                }

                canvas.drawBitmap(spaceBackground, 0, spaceBackgroundY, null);


                if (spaceBackgroundY < screenHeight - newScreenHeight)//אם קורדינאטת הY של התמונה קטן מ-0
                {

                    canvas.drawBitmap(spaceBackground, 0, spaceBackgroundY + newScreenHeight, null);
                    //אז נצייר את התמונה כל פעם בכך שנסיר מגודל המסך את מיקומה הנוכחי וכך ניצור אשליה שהתמונה זזה מלמעלה מטה

                }


                spaceship.draw(canvas);
                synchronized (LOCK) {
                    for (SpaceshipBullet b : spaceshipBullets) {
                        b.update();

                        b.draw(canvas);
                    }

                }


                synchronized (LOCK) {
                    for (Invader inv : invaders) {
                        inv.update();
                        for (InvaderBullet b : invaderBullets)
                            b.draw(canvas);


                        inv.draw(canvas);
                    }
                    addInvader();

                }
                synchronized (LOCK) {
                    Log.d("banana", "num bullets:" + invaderBullets.size());



                 //   addInvaderBullet();

                }


                surfaceHolder.unlockCanvasAndPost(canvas);

            }

            SystemClock.sleep((long) (1000.0 / FPS));
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN://player touched the screen

                spaceship.draw(canvas);
                synchronized (LOCK) {
                    addSpaceshipBullet();

                }
                updateSpaceshipBullet();

                update(x, y);

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                synchronized (LOCK) {
                    addSpaceshipBullet();
                }

                updateSpaceshipBullet();


                update(x, y);
                invalidate();
                break;
            default:

                invalidate();

                // Do nothing.
        }

        return true;
    }

    public void update(int x, int y) {
        spaceship.update(x, y);

    }

    public void updateSpaceshipBullet() {

        for (SpaceshipBullet b : spaceshipBullets) {
            b.update();

        }
    }

    private int bulletCounter = 0;

    public void addSpaceshipBullet() {

        bulletCounter++;
        SpaceshipBullet b = new SpaceshipBullet();
        b.setXForBullet(spaceship.getX());
        b.setYForBullet(spaceship.getY());
        if (bulletCounter == FPS / 20) {
            spaceshipBullets.add(b);
            bulletCounter = 0;
        }


    }

    private int invaderBulletCounter = 0;

    public void addInvaderBullet(int posX, int posY) {

       // invaderBulletCounter++;


           // if (invaderBulletCounter == FPS) {
                invaderBullets.add(new InvaderBullet(posX, posY));
                invaderBulletCounter = 0;
           // }




    }


    private int invaderCounter = 0;

    public void addInvader() {

        invaderCounter++;
        Invader a = new Invader();

        if (invaderCounter == FPS / 2) {
            invaders.add(a);
            invaderCounter = 0;
        }


    }


    public boolean isInvaderDamaged() {
        for (Invader i : invaders) {
         for (SpaceshipBullet b : spaceshipBullets) {

                int bulletIndexX = b.getX();
                int invaderIndexX = i.getX();
                int bulletIndexY = b.getY();
                int invaderIndexY = i.getY();
                int widthOfInvader = invadersBitmap.get(0).getWidth();
                int widthOfBullet = bulletSpaceship.get(0).getWidth();
                int heightOfInvader = invadersBitmap.get(0).getHeight();

                if ((bulletIndexY==invaderIndexY+heightOfInvader)&&(invaderIndexX <= bulletIndexX +widthOfBullet* 0.5 && invaderIndexX + widthOfInvader >= bulletIndexX +widthOfBullet* 0.5)) {
                    return true;

                }
            }

        }
        return false;
    }

    public void pause() {
        mRunning = false;
        while (true) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameThread = null;


    }

    public void resume() {
        mRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
