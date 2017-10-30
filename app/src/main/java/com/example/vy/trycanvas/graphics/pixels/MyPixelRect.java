package com.example.vy.trycanvas.graphics.pixels;

import android.graphics.Bitmap;

/**
* Кастомный пикель прямоугольной формы
* */
public class MyPixelRect extends MyPixel
{
    public MyPixelRect(int size,int x, int y) {
        super(size,x,y);

        points = new float[2*size*size];

        int count = 0;
        if(size%2==0){
            for(int i = -size/2; i < size/2; i++){
                for(int j = - size/2; j < size/2; j++){
                    points[count] = x + i;
                    count++;
                    points[count] = y + j;
                    count++;
                }
            }
        }else{
            for(int i = -size/2; i < size/2+1; i++){
                for(int j = - size/2; j < size/2+1; j++){
                    points[count] = x + i;
                    count++;
                    points[count] = y + j;
                    count++;
                }
            }
        }
    }

    public MyPixelRect(int size,int x, int y, int color, Bitmap bitmap) {
        super(size,x,y);

        setColor(color);

        if(size%2==0){
            for(int i = -size/2; i < size/2; i++){
                for(int j = - size/2; j < size/2; j++){
                    setBitmapPixel(bitmap,x + i,y + j,color);
                }
            }
        }else{
            for(int i = -size/2; i < size/2+1; i++){
                for(int j = - size/2; j < size/2+1; j++){
                    setBitmapPixel(bitmap,x + i,y + j,color);
                }
            }
        }
    }

}
