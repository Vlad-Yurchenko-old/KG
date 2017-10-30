package com.example.vy.trycanvas.graphics.smoothing;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.figures.Figure;

public class FigureLineSelect extends Figure {
    Bitmap buf;

    public Figure draw(Bitmap bmp, int[] points) {
        buf = Bitmap.createBitmap(bmp.getWidth()*2, bmp.getHeight()*2, Bitmap.Config.ARGB_8888);

        Drawer.getLine(points[0]*2, points[1] *2, points[2]*2, points[3]*2,color,buf);
        int x1, y1, x2, y2;
        if (points[2] > points[0]) {
            x1 = points[0];
            x2 = points[2];
        } else {
            x1 = points[2];
            x2 = points[0];
        }
        if (points[3] > points[1]) {
            y1 = points[1];
            y2 = points[3];
        } else {
            y1 = points[3];
            y2 = points[1];
        }

        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                setPixel(buf, bmp, i, j,color);
            }
        }
        return this;
    }

    private void setPixel(Bitmap buf, Bitmap bitmap, int x, int y, int color) {
        int bufX = x * 2;
        int bufY = y * 2;
        int alpha = (
                Color.alpha(buf.getPixel(bufX, bufY))
                        + Color.alpha(buf.getPixel( bufX, bufY + 1))
                        + Color.alpha(buf.getPixel( bufX + 1, bufY))
                        + Color.alpha(buf.getPixel( bufX + 1, bufY + 1))
        ) / 4;
        if(alpha!=0){
            setBitmapPixel(bitmap,x,y,Color.argb(
                    alpha,
                    Color.red(color),
                    Color.green(color),
                    Color.blue(color)
            ));
        }else{
            setBitmapPixel(bitmap,x,y,Color.WHITE);
        }

    }
}
