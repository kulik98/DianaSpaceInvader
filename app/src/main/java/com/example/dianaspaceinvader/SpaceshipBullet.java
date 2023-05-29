package com.example.dianaspaceinvader;

import static com.example.dianaspaceinvader.Images.bulletSpaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class SpaceshipBullet extends Sprite {


    private Bitmap[] bulletBitmaps;
    private int bitmapIndex;
    private int positionXaxis;
    private int positionYaxis;

    private Bitmap bulletBitmap;

    int widthOfBullet;
    int heightOfBullet;


    private boolean visible;

    public SpaceshipBullet() {

        visible = true;


        bulletBitmaps = new Bitmap[]{
                bulletSpaceship.get(0),bulletSpaceship.get(1)
        };

        bitmapIndex = 0;


        bulletBitmaps[0] =    bulletSpaceship.get(0);

        bulletBitmaps[1] = bulletSpaceship.get(1);


    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isScreenBounds() {
        return y < 0;
    }

    public void update() {
        positionYaxis -= 20;

        if (positionYaxis < 0)
            visible = false;

    }

    public void setXForBullet(int x)
    {
        positionXaxis =x;
    }
    public void setYForBullet(int y)
    {
        positionYaxis =y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bulletBitmaps[bitmapIndex], positionXaxis, positionYaxis, null);
    }

    public void changeBitmap() {
        bitmapIndex = 1 - bitmapIndex;
    }
    public int getX()
    {
        return positionXaxis;
    }

    public int getY()
    {
        return positionYaxis;
    }

}
