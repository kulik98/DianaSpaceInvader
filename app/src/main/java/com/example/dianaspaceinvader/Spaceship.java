package com.example.dianaspaceinvader;

import static com.example.dianaspaceinvader.Images.spaceship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Spaceship {

    private int positionXaxis;
    private int positionYaxis;

    private Bitmap[] spaceshipBitmaps;
    private int bitmapIndex;

    private Bitmap spaceshipBitmap;

    int widthOfSpaceship;
    int heightOfSpaceship;


    public Spaceship( int screenX, int screenY)
    {



        positionXaxis = screenX/2 - widthOfSpaceship /2;
        positionYaxis = screenY-300;

        spaceshipBitmaps = new Bitmap[] { spaceship.get(0), spaceship.get(1)};

        bitmapIndex = 0;





    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(spaceshipBitmaps[bitmapIndex], positionXaxis,positionYaxis,null);
    }




    public void update(int newX ,int newY)//updating the location of the spaceship according to users intstarctions
    {
        positionXaxis = newX;

    }

    public void changeBitmap()
    {
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

    public int getHeightOfSpaceship()
    {
        return heightOfSpaceship;
    }
    public int getWidthOfSpaceship()
    {
        return widthOfSpaceship;
    }
    public int getposX()
    {
        return positionXaxis;
    }
    public int getPosY()
    {
        return positionYaxis;
    }





}
