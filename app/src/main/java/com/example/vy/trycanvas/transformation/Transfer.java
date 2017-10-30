package com.example.vy.trycanvas.transformation;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.concurrent.CopyOnWriteArrayList;

public class Transfer extends Transformation{

    public static synchronized void transfer(Bitmap bitmap, CopyOnWriteArrayList<Point2D> arrayList, int x, int y, int color, boolean flag){
        for(Point2D point2D : arrayList){
            int tempX = point2D.getX()+x, tempY = point2D.getY()+y;
            //int color = Figure.getBitmapPixel(bitmap,point2D.getX(),point2D.getY());
            setBitmapPixel(bitmap, point2D.getX(), point2D.getY(), Color.WHITE);
            //Log.d("VY_LOGS", String.valueOf("==" + x + " " + y + "\t" + tempX + " " + tempY));
            setBitmapPixel(bitmap,tempX, tempY, color);

            if(flag){
                point2D.setX(tempX);
                point2D.setY(tempY);
            }
        }
    }

    public static synchronized void clearBitInFigure(Bitmap bitmap, CopyOnWriteArrayList<Point2D> arrayList){
        for(Point2D point2D : arrayList) {
            setBitmapPixel(bitmap, point2D.getX(), point2D.getY(), Color.argb(0,0,0,0));
        }
    }

}
