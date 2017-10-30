package com.example.vy.trycanvas.fractals;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

public class Fern extends Fractal{

    public void draw(Bitmap bitmap, int color) {
        int h = 1200;
        int w = 740;
        double x = 0;
        double y = 0;

        for (int i = 0; i < 20_000; i++) {
            double tmpx, tmpy;
            double r = Math.random();
            if (r <= 0.01) {
                tmpx = 0;
                tmpy = 0.16 * y;
            } else if (r <= 0.08) {
                tmpx = 0.2 * x - 0.26 * y;
                tmpy = 0.23 * x + 0.22 * y + 1.6;
            } else if (r <= 0.15) {
                tmpx = -0.15 * x + 0.28 * y;
                tmpy = 0.26 * x + 0.24 * y + 0.44;
            } else {
                tmpx = 0.85 * x + 0.04 * y;
                tmpy = -0.04 * x + 0.85 * y + 1.6;
            }
            x = tmpx;
            y = tmpy;

            setBitmapPixel(bitmap,(int) Math.round(w / 2 + x * w / 11), (int) Math.round(h - y * h / 11), color);
        }
    }
}

