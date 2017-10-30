package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

/**
* Класс для формирования фигуры кривая
* */
public class FigureCurve extends Figure {

    public Figure getBezierLine(int[] points, Bitmap bitmap) {

        float step = 0.001F;
        float r[] = new float[points.length];
        float t = step;
        while (t <= 1) {
            for (int i = 0; i < r.length; i++) {
                r[i] = points[i];
            }
            for (int i = r.length - 2; i >= 0; i -= 2) {
                for (int j = 0; j < i; j++) {
                    r[j] += t * (r[j + 2] - r[j]);
                    j++;
                    r[j] += t * (r[j + 2] - r[j]);
                }
            }
            pixels.add(new Point2D((int)r[0],(int)r[1]));
            setBitmapPixel(bitmap,(int)r[0],(int)r[1],color);
            //bitmap.setPixel((int)r[0],(int)r[1],color);
            t += step;
        }

        return this;
    }

}
