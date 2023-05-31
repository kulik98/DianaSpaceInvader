package com.example.dianaspaceinvader;

import static com.example.dianaspaceinvader.Images.bulletInvaders;
import static com.example.dianaspaceinvader.Images.bulletSpaceship;
import static com.example.dianaspaceinvader.Images.invader0001;
import static com.example.dianaspaceinvader.Images.invader1011;
import static com.example.dianaspaceinvader.Images.invader2021;
import static com.example.dianaspaceinvader.Images.invadersBitmap;
import static com.example.dianaspaceinvader.Images.spaceship;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class MainActivity extends AppCompatActivity {

    private int[] invaderBitmaps = {R.drawable.alien00, R.drawable.alien01,
            R.drawable.alien10,
            R.drawable.alien11,
            R.drawable.alien20,
            R.drawable.alien21};
    private int[] spaceshipBitmaps = {R.drawable.spaceship2, R.drawable.spaceship2animation};

    private int[] bulletSBitmaps = {R.drawable.laserbullet, R.drawable.laserbulletanimation};
    private int[] bulletIBitmaps = {R.drawable.firebullet, R.drawable.firebulletanimation};
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int bitmap : invaderBitmaps) {

                    Bitmap invaderBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), bitmap);
                    float heightInvaderOriginal = invaderBitmap.getHeight();
                    float widthInvaderOriginal = invaderBitmap.getWidth();
                    int widthOfInvader = (int) widthInvaderOriginal / 4;
                    int heightOfInvader = (int) heightInvaderOriginal / 4;
                    invaderBitmap = Bitmap.createScaledBitmap(invaderBitmap, widthOfInvader, heightOfInvader, false);
                    invadersBitmap.add(invaderBitmap);
                }
                invader0001.add(invadersBitmap.get(0));
                invader0001.add(invadersBitmap.get(1));
                invader1011.add(invadersBitmap.get(2));
                invader1011.add(invadersBitmap.get(3));
                invader2021.add(invadersBitmap.get(4));
                invader2021.add(invadersBitmap.get(5));


                for (int bitmap : spaceshipBitmaps) {
                    Bitmap spaceshipBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), bitmap);
                    float heightShipOriginal = spaceshipBitmap.getHeight();
                    float widthShipOriginal = spaceshipBitmap.getWidth();
                    int widthOfSpaceship = (int) widthShipOriginal / 10;
                    int heightOfSpaceship = (int) heightShipOriginal / 10;
                    spaceshipBitmap = Bitmap.createScaledBitmap(spaceshipBitmap, widthOfSpaceship, heightOfSpaceship, false);

                    spaceship.add(spaceshipBitmap);

                }

                for (int bulletSBitmap : bulletSBitmaps) {
                    Bitmap bulletBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), bulletSBitmap);
                    float heightBulletOriginal = bulletBitmap.getHeight();
                    float widthBulletOriginal = bulletBitmap.getWidth();
                    int widthOfBullet = (int) widthBulletOriginal / 2;
                    int heightOfBullet = (int) heightBulletOriginal / 2;
                    bulletBitmap = Bitmap.createScaledBitmap(bulletBitmap, widthOfBullet, heightOfBullet, false);

                    bulletSpaceship.add(bulletBitmap);
                }
                for (int bulletIBitmap : bulletIBitmaps) {
                    Bitmap bulletBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), bulletIBitmap);
                    float heightBulletOriginal = bulletBitmap.getHeight();
                    float widthBulletOriginal = bulletBitmap.getWidth();
                    int widthOfBullet = (int) widthBulletOriginal / 6;
                    int heightOfBullet = (int) heightBulletOriginal / 6;
                    bulletBitmap = Bitmap.createScaledBitmap(bulletBitmap, widthOfBullet, heightOfBullet, false);
                    bulletInvaders.add(bulletBitmap);
                }

                findViewById(android.R.id.content).post(new Runnable() {
                    @Override
                    public void run() {
                        gameView = new GameView(MainActivity.this);
                        setContentView(gameView);

                        // Android 4.1 and higher simple way to request fullscreen.
                        gameView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

                        gameView.resume();
                    }
                });

            }


        }).start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}