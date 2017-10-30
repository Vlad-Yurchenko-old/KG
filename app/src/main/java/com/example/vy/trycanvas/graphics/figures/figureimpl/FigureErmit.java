package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.figures.Figure;

/**
 * Created by HOME on 16.04.2017.
 */

public class FigureErmit extends Figure {



    public Figure draw(int[] points, Bitmap bmp) {

        float step = .001F;
        float t;
        int x, y;
        for (int i = 0; i < points.length - 4; i += 4) {
            t = .0F;
            while (t <= 1) {
                x = (int) ((1 - 3 * t * t + 2 * t * t * t) * points[i]
                        + t * t * (3 - 2 * t) * points[i + 4]
                        + t * (1 - 2 * t + t * t) * (points[i + 2] - points[i])*3
                        - t * t * (1 - t) * (points[i + 6] - points[i + 4])*3);

                y = (int) ((1 - 3 * t * t + 2 * t * t * t) * points[i + 1]
                        + t * t * (3 - 2 * t) * points[i + 5]
                        + t * (1 - 2 * t + t * t) * (points[i + 3] - points[i + 1])*3
                        - t * t * (1 - t) * (points[i + 7] - points[i + 5])*3);
                try {
                    bmp.setPixel(x, y, color);
                } catch (Exception e) {
                }
                t += step;
            }
        }
        return this;
    }
}
