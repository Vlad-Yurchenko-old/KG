package com.example.vy.trycanvas.transformation;

import android.graphics.Bitmap;
import android.util.Log;

public abstract class Transformation {

    protected static void setBitmapPixel(Bitmap bitmap, int x, int y, int color){
        if(x > 0 && y > 0){
            if(x < bitmap.getWidth() && y < bitmap.getHeight()){
                bitmap.setPixel(x,y,color);
            }
        }
    }

    protected static int getBitmapPixel(Bitmap bitmap, int x, int y){
        if(x > 0 && y > 0){
            if(x < bitmap.getWidth() && y < bitmap.getHeight()){
                return bitmap.getPixel(x,y);
            }
        }
        return 0;
    }


}
