package com.example.dianaspaceinvader;

import static com.example.dianaspaceinvader.GameView.newScreenWidth;

import static com.example.dianaspaceinvader.Images.invader0001;

import static com.example.dianaspaceinvader.Images.invader1011;

import static com.example.dianaspaceinvader.Images.invader2021;

import static com.example.dianaspaceinvader.Images.invadersBitmap;
import static java.lang.reflect.Modifier.FINAL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;


public class Invader {//in this class we will have different types of invaders. some invaders belong in different levels/

    private int positionXaxis;
    private int positionYaxis;

    final int numberOfAliens = 3;
    private Bitmap[] level1;
    private Bitmap[] level2;
    private Bitmap[] level3;
    private Bitmap[] level4;

private List[] randomInvader = {invader0001,invader1011,invader2021,};
    private Bitmap[] currentInvaderBitmap;
    private int bitmapIndex;









    private boolean visible;

    public Invader() {




        int alienType = new Random().nextInt(numberOfAliens);

        randomInvader[alienType].get(0);
        randomInvader[alienType].get(1);

    positionXaxis = new Random().nextInt(newScreenWidth);
        positionYaxis = 0;
        visible = true;


        currentInvaderBitmap = new Bitmap[]{(Bitmap) randomInvader[alienType].get(0), (Bitmap)  randomInvader[alienType].get(1)};

        bitmapIndex = 0;


    }


    public void setLevel1Invaders(Context context)
    {
        level1 = new Bitmap[]{ BitmapFactory.decodeResource(context.getResources(), R.drawable.alien10),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.alien11), BitmapFactory.decodeResource(context.getResources(), R.drawable.alien00),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.alien01)};


    }
    public boolean isVisible() {
        return visible;

    }
    public void setVisibility(boolean isV)
    {
        visible = isV;
    }

    public void update() {
        positionYaxis += 4;

        if (positionYaxis > 1900)
            visible = false;
    }



    public void draw(Canvas canvas) {
        canvas.drawBitmap(currentInvaderBitmap[bitmapIndex], positionXaxis, positionYaxis, null);
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
