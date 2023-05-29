package com.example.dianaspaceinvader;

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

import android.view.SurfaceHolder;
import android.view.SurfaceView;




public class StartView extends SurfaceView implements Runnable {




    private Bitmap spaceBackground;

    private int spaceBackgroundY;

    private int screenHeight;
    private int screenWidth;
    private int newScreenHeight;
    private int newScreenWidth;


    private boolean mRunning = false;
    private Thread gameThread = null;

    private Context context;
    private SurfaceHolder surfaceHolder;
    Canvas canvas;




    public StartView(Context context) {
        super(context);
        init(context);
        spaceBackground = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.startbackground);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;//גודל המסך רוחב
        screenHeight = size.y;//גודל המסך אורך
        float backgroundHeight = spaceBackground.getHeight();//גודל אורך התמונה
        float backgroundWidth = spaceBackground.getWidth();//גודל רוחב התמונה

        float ratio = backgroundWidth / backgroundHeight;//יחס רוחב התמונה לאורך שלה

        newScreenHeight = screenHeight;
        newScreenWidth = (int) (ratio * screenHeight);//גודל אורך המסך כפול גודל רוחב התמונה חלקי גודל אורך התמונה
        spaceBackground = Bitmap.createScaledBitmap(spaceBackground, newScreenWidth, newScreenHeight, false);

    }

    public StartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public StartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context mcontext) {
        context = mcontext;
        surfaceHolder = getHolder();
    }


    @Override
    public void run() {



        while (mRunning) {

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


            }

          //  surfaceHolder.unlockCanvasAndPost(canvas);

        }


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
