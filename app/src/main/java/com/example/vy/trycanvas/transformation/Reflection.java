package com.example.vy.trycanvas.transformation;

import android.graphics.Bitmap;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.concurrent.CopyOnWriteArrayList;

public class Reflection extends Transformation{

    public static void reflect(Bitmap bitmap, CopyOnWriteArrayList<Point2D> arrayList, Point2D point1, Point2D point2){
        int a = point1.getY() - point2.getY();
        int b = point2.getX() - point1.getX();
        int c = point1.getX()*point2.getY() - point2.getX()*point1.getY();

        int color, tempX, tempY;

        double const1 = (double)(b*b - a*a) / (double)(b*b + a*a);
        double const2 = (double)2*a*b / (double)(b*b + a*a);
        double const3 = (double)2*a*c / (double)(b*b + a*a);
        double const4 = (double)2*b*c / (double)(b*b + a*a);

        for (Point2D point: arrayList) {
            color = getBitmapPixel(bitmap,point.getX(), point.getY());

            tempX = (int) (point.getX()*const1 - point.getY() * const2 - const3);
            tempY = (int) (-1 * point.getX() * const2 - point.getY() * const1 - const4);

            setBitmapPixel(bitmap,tempX,tempY, color);
        }

    }

}
