package com.example.dianaspaceinvader;


import static com.example.dianaspaceinvader.Images.bulletInvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class InvaderBullet extends Sprite {


    private Bitmap[] bulletBitmaps;
    private int bitmapIndex;

    private boolean visible;

    public InvaderBullet(int x, int y) {

        this.x = x;
        this.y = y;

        visible = true;


        bulletBitmaps = new Bitmap[]{
                bulletInvaders.get(0),bulletInvaders.get(1)
        };

        bitmapIndex = 0;


        bulletBitmaps[0] =    bulletInvaders.get(0);

        bulletBitmaps[1] = bulletInvaders.get(1);


    }

    public boolean isScreenBounds() {
        return y < GameView.SCREEN_HEIGHT;
    }

    public void update() {
        y += 10;
    }

//    public void setXForBullet(int x)
//    {
//        this.x =x;
//    }
//    public void setYForBullet(int y)
//    {
//        this.y = y;
//    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bulletBitmaps[bitmapIndex], x, y, null);
    }

    public void changeBitmap() {
        bitmapIndex = 1 - bitmapIndex;
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

}

