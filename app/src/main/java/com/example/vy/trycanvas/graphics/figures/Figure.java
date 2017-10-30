package com.example.vy.trycanvas.graphics.figures;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


/**
* Абстрактный класс фигуры
 * предназначен для хранения пикселей и цвета фигуры
 * colorFill - цвет заливки фигуры (круг, прямоугольник и тд)
 * color - цвет границы фигуры (круг, прямоугольник и тд)
 * color - цвет линии (линия)
 * pixels - массив точей из которых состоит данная фигура
* */
public abstract class Figure {

    protected int colorFill = 0xFF000000;
    protected int color = 0xFF000000;

    protected CopyOnWriteArrayList<Point2D> pixels;

    protected int [] refPoints;

    public Figure(){
        pixels = new CopyOnWriteArrayList<>();
    }

    public int getColorFill() {
        return colorFill;
    }
    public int getColor() {
        return color;
    }

    public Figure setColorFill(int colorFill) {
        this.colorFill = colorFill;
        return this;
    }
    public Figure setColor(int color) {
        this.color = color;
        return this;
    }

    public CopyOnWriteArrayList<Point2D> getFigure() {
        return pixels;
    }

    public static void setBitmapPixel(Bitmap bitmap, int x, int y, int color){
        if(x > 0 && y > 0){
            if(x < bitmap.getWidth() && y < bitmap.getHeight()){
                bitmap.setPixel(x,y,color);
            }
        }
    }

    public static int getBitmapPixel(Bitmap bitmap, int x, int y){
        if(x > 0 && y > 0){
            if(x < bitmap.getWidth() && y < bitmap.getHeight()){
                return bitmap.getPixel(x,y);
            }
        }
        return 0;
    }

    public boolean contains(int x, int y){
        for (Point2D point2D : pixels ) {
            if(point2D.getX() == x && point2D.getY() == y){
                return true;
            }
        }
        return false;
    }

    public int[] getRefPoints() {
        return refPoints;
    }

    public void setRefPoints(int[] refPoints) {
        this.refPoints = refPoints;
    }
}
