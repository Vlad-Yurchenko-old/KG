package com.example.vy.trycanvas.fractals;


import android.graphics.Bitmap;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

public class KochSnowflake extends Fractal{

    private static void drawKochCurve(Bitmap bitmap, Point2D p, Point2D q, int n, int color) {
        if (n == 0) {
            Drawer.getLine(p.getX(),p.getY(),q.getX(),q.getY(),color,bitmap);
            //graph.draw(new Line2D.Double(p, q));
            return;
        }
        Point2D r = new Point2D(
                (2 * p.getX() + q.getX()) / 3,
                (2 * p.getY() + q.getY()) / 3);
        Point2D s = new Point2D((int)((p.getX() + q.getX()) / 2 - (p.getY() - q.getY()) * Math.sqrt(3) / 6),
                (int) (((p.getY() + q.getY()) / 2 + (p.getX() - q.getX()) * Math.sqrt(3) / 6)));
        Point2D t = new Point2D(
                (p.getX() + 2 * q.getX()) / 3,
                (p.getY() + 2 * q.getY()) / 3);
        drawKochCurve(bitmap,p, r, n - 1,color);
        drawKochCurve(bitmap,r, s, n - 1,color);
        drawKochCurve(bitmap,s, t, n - 1,color);
        drawKochCurve(bitmap,t, q, n - 1,color);
    }

    public void drawKochSnowflake(Bitmap bitmap, int color,Point2D c, double d, int m, int n) {
        Point2D[] vs = new Point2D[m];
        for (int i = 0; i < m; ++i) {
            vs[i] = new Point2D(
                    (int)(c.getX() + d * Math.cos(2 * Math.PI / m * i)),
                    (int)(c.getY() - d * Math.sin(2 * Math.PI / m * i)));
        }
        for (int i = 0; i < m; ++i) {
            drawKochCurve(bitmap,vs[(i + 1) % m], vs[i], n, color);
        }
    }

}
