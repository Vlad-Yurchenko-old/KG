package com.example.vy.trycanvas.fractals;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.example.vy.trycanvas.graphics.pixels.PointF;

public class MandelbrotSet extends Fractal{

    public static int count = 0;
    static float startX;
    static float startY;
    private int ITER;
    private float zoom;

    public MandelbrotSet(int ITER) {
        this.ITER = ITER;
        zoom = 0.05f;
        startX = 1;
        startY = 1;
    }

    public void draw(Bitmap bitmap, int color, PointF point) {
        bitmap.eraseColor(Color.BLACK);
        zoom = 0.004f;
//        int mx = (int)(bitmap.getWidth() );
//        int my = (int)(bitmap.getHeight()+8000) ;
//        int mx = (int)(bitmap.getWidth() /2);
//        int my = (int)(bitmap.getHeight()/2) ;
//        int mx = (int) (bitmap.getWidth() / 2+points[0].x);
//        int my = (int) (bitmap.getHeight() / 2+points[0].y);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        startX = (-point.x / width / startX) - (zoom * width / 2);
        startY = (-point.y / height / startY) - (zoom * height / 2);
        System.out.println(startX + " " + startY);

        for (int i = 0; i < count; i++) {
            zoom *= 0.5;
        }
        count++;
        /*if (zoom <= 0.000625) {
            count = 0;
        }*/

//        int mx = (int) points[0].x;
//        int my = (int) points[0].y;
        PointF c = new PointF();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int n = 0;
                c.x = x * zoom + startX;
                c.y = y * zoom + startY;
                PointF z = new PointF(0, 0);
                while (z.x * z.x + z.y * z.y < 4 && n < ITER) {
                    PointF t = new PointF(z.x, z.y);
                    z.x = t.x * t.x - t.y * t.y + c.x;
                    z.y = 2 * t.x * t.y + c.y;
                    n++;
                }
                //System.out.println(n+": "+mx + x+" "+ my + y);
                if (n < ITER) {
                    double r = (0.1 + n * 0.06) * 100; //расчет
                    double g = (0.2 + n * 0.09) * 100; //значений
                    double b = (0.3 + n * 0.03) * 100; //для раскраски множества
                    r = (r) % 255;
                    g = (g) % 255;
                    b = (b) % 255;
//                    view.getBitmap().setPixel(x, y, Color.rgb((int) (r), (int) (g), (int) (b)));
                    if (y < bitmap.getHeight() && x < bitmap.getWidth()) {
                        bitmap.setPixel(x, y, Color.rgb((int) (r), (int) (g), (int) (b)));
//                        bitmap.setPixel(mx + x, my + y, (n*200)%255);
                    }
                }
            }
        }
//        view.setBitmap(bitmap);
    }

}
