package com.example.vy.trycanvas.fractals;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.pixels.PointF;

public class SierpinskiCarpet extends Fractal {

    public void drawCarpet(Bitmap bitmap, int color, int iter, PointF... points) {
        draw(bitmap,color,iter, points);
    }

    private void draw(Bitmap bitmap, int color, int n, PointF... points) {
        float x1 = points[0].x * 2 / 3 + points[1].x / 3;
        float x2 = points[0].x / 3 + 2 * points[1].x / 3;
        float y1 = points[0].y * 2 / 3 + points[1].y / 3;
        float y2 = points[0].y / 3 + 2 * points[1].y / 3;
        Drawer.getRectangle((int)x1, (int)y1, (int)x2, (int)y2,color,bitmap);
        if (n > 0) {
            draw(bitmap,color,n - 1, new PointF(points[0].x, points[0].y), new PointF(x1, y1));//
            draw(bitmap,color,n - 1, new PointF(x1, points[0].y), new PointF(x2, y1));//
            draw(bitmap,color,n - 1, new PointF(x2, points[0].y), new PointF(points[1].x, y1));//
            draw(bitmap,color,n - 1, new PointF(points[0].x, y1), new PointF(x1, y2));
            draw(bitmap,color,n - 1, new PointF(x2, y1), new PointF(points[1].x, y2));
            draw(bitmap,color,n - 1, new PointF(points[0].x, y2), new PointF(x1, points[1].y));
            draw(bitmap,color,n - 1, new PointF(x1, y2), new PointF(x2, points[1].y));
            draw(bitmap,color,n - 1, new PointF(x2, y2), new PointF(points[1].x, points[1].y));
        }

    }
}
