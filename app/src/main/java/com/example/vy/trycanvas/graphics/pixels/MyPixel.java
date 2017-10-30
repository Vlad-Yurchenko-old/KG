package com.example.vy.trycanvas.graphics.pixels;


import android.graphics.Bitmap;

/**
* Абстрактный класс пикселя
 * предназначен для создания пикселей произвольного размера
 * size - размерность пикселя (если пиксель квадратный, то число реальных пикселей
 *                             будет равняться size*size)
 * point2D - координата центра пикселя
* */
public abstract class MyPixel {
    protected int size;
    protected Point2D point2D;
    protected int color = 0xFF000000;
    protected float [] points;


    public MyPixel(int size, int x, int y){
        this.size = size;
        this.point2D = new Point2D(x,y);
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public int getX() {
        return point2D.getX();
    }
    public int getY() {
        return point2D.getY();
    }

    public  float[] getPixel(){
        return points;
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


}
