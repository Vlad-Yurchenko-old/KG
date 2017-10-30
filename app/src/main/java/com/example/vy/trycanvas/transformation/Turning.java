package com.example.vy.trycanvas.transformation;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.concurrent.CopyOnWriteArrayList;

public class Turning extends Transformation {

    public void transformate(Bitmap bitmap, CopyOnWriteArrayList<Point2D> arrayList, int color, int b){

        double l = b * (Math.PI / 180);

        for(Point2D point2D : arrayList){
            int tempX = (int) Math.round( point2D.getX() * Math.cos(l) - point2D.getY() * Math.sin(l)) ;
            int tempY = (int) Math.round( Math.abs(point2D.getX() * Math.sin(l) + point2D.getY() * Math.cos(l)) );

            setBitmapPixel(bitmap, point2D.getX(), point2D.getY(), Color.WHITE);
            setBitmapPixel(bitmap,tempX, tempY, color);

            point2D.setX(tempX);
            point2D.setY(tempY);
        }

    }

}
