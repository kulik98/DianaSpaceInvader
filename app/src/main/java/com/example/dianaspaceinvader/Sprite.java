package com.example.dianaspaceinvader;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

public class Sprite {

    protected int x;
    protected int y;
    protected Bitmap bitmap;

    protected String currentState;

    protected Map<String, Bitmap[]> bitmaps; //TODO: change String to Enum


    public Sprite()
    {
        bitmaps = new HashMap<>();
    }


    public void draw(Canvas canvas)
    {

        canvas.drawBitmap(bitmap, x, y, null);
    }

    public void update(int newX ,int newY)//updating the location of the spaceship according to users intstarctions
    {
        x = newX;
        y = newY;
    }



}
