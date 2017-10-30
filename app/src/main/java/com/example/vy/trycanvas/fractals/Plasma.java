package com.example.vy.trycanvas.fractals;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

public class Plasma extends Fractal{

    public void draw(Bitmap bitmap) {
        Random random = new Random();
        double c1 = random.nextFloat();
        double c2 = random.nextFloat();
        double c3 = random.nextFloat();
        double c4 = random.nextFloat();

        draw(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getHeight() / 2, 1, c1, c2, c3, c4, bitmap);
    }

    private void draw(double x, double y, double size, double stddev, double c1, double c2, double c3, double c4, Bitmap bitmap) {
        Random random = new Random();
        if (size < 0.4) {
            return;
        }
        double cM = ((c1 + c2 + c3 + c4) / 4.0 + stddev * random.nextFloat());
        int color = Color.HSVToColor(new float[]{(float) (cM * 360), 0.8f, 0.8f});
        //bitmap.setPixel((int)x,(int)y,color);

        setBitmapPixel(bitmap,(int)x,(int)y,color);

        double cT = ((c1 + c2) / 2.0);    // top
        double cB = ((c3 + c4) / 2.0);    // bottom
        double cL = ((c1 + c3) / 2.0);    // left
        double cR = ((c2 + c4) / 2.0);    // right

        draw(x - size / 2, y - size / 2, size / 2, stddev / 2, cL, cM, c3, cB,bitmap);
        draw(x + size / 2, y - size / 2, size / 2, stddev / 2, cM, cR, cB, c4,bitmap);
        draw(x - size / 2, y + size / 2, size / 2, stddev / 2, c1, cT, cL, cM,bitmap);
        draw(x + size / 2, y + size / 2, size / 2, stddev / 2, cT, c2, cM, cR,bitmap);
    }

}
