package com.example.vy.trycanvas.fractals;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Biomorph extends Fractal {
    private int xc = 0;
    private int yc = 0;
    private int width;
    private int height;

    public void draw(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        xc = width / 2;
        yc = height / 2 - 50;
        //Определяем коэффициент C
        float cx = 0.5f;
        float cy = 0f;

        //Основной цикл по всем точкам комплексной плоскости
        for (float x = -2; x <= 2; x += 0.002f) {
            for (float y = -2; y <= 2; y += 0.002f) {
                float zx = x;
                float zy = y;
                int n = 0;
                int it = 20;

                //Итерируем функцию f(z) = z^3 + c
                while ((Math.abs(zx) < 100) && (Math.abs(zy) < 100) && (n < it)) {
                    float tx = zx;
                    float ty = zy;
                    n++;
                    zx = tx * tx * tx - 3 * tx * ty * ty + cx;
                    zy = 3 * tx * tx * ty - ty * ty * ty + cy;

                }
                //Определяем зоны черного и оранжевого
                if ((Math.abs(zx) >= 300) || (Math.abs(zy) >= 10000)) {
                    int xt = ChangeCoordinates(x, 0);
                    int yt = ChangeCoordinates(y, 1);
                    if (yt < height && xt < width && xt > 0 && yt > 0)
                        bitmap.setPixel(xt, yt, Color.BLACK);

                } else {
                    int xt = ChangeCoordinates(x, 0);
                    int yt = ChangeCoordinates(y, 1);
                    if (yt < height && xt < width && xt > 0 && yt > 0)
                        bitmap.setPixel(xt, yt, Color.GREEN);
                }
            }
        }
    }

    //Перевод координат из реальных в машинные
    public int ChangeCoordinates(double a, int isY) {
        if (isY == 1) return (int) (yc - a * 150);
        return (int) (xc + a * 150);
    }
}
