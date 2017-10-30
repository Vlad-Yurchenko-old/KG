package com.example.vy.trycanvas.transformation;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.ArrayList;

public class Scaling extends Transformation {

    public static void scale(Bitmap bitmap, ArrayList<Figure> figures, double scale){
        for(Figure figure: figures){
            for(Point2D point2D : figure.getFigure()){
                int tempX = point2D.getX();
                int tempY = point2D.getY();

                int color = getBitmapPixel(bitmap,tempX, tempY);
                setBitmapPixel(bitmap,tempX, tempY, Color.WHITE);

                tempX*=scale;
                tempY*=scale;

               // Log.d("VY_LOGS", String.valueOf(scale));

                setBitmapPixel(bitmap,tempX,tempY,color);
                point2D.setX(tempX);
                point2D.setY(tempY);
            }
        }
    }

}
