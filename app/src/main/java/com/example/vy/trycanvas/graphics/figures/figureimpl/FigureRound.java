package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

/**
* Класс для формирования фигуры круг
 * x0, y0 - координаты центра окружности
 * R - радиус окружности
* */
public class FigureRound extends Figure {

    private int x0,y0,R;
    public FigureRound(int x0, int y0, int R){
        this.x0 = x0;
        this.y0 = y0;
        this.R = R;
    }


    public Figure BrazAlgCircle(Bitmap bitmap){
        pixels.clear();
        int x = 0;
        int y = R;
        int delta = 1 - 2 * R;
        int error;
        while(y >= 0){
            pixels.add(new Point2D(x0 + x,y0 + y));
            pixels.add(new Point2D(x0 + x,y0 - y));
            pixels.add(new Point2D(x0 - x,y0 + y));
            pixels.add(new Point2D(x0 - x,y0 - y));

            setBitmapPixel(bitmap,x0 + x,y0 + y,color);
            setBitmapPixel(bitmap,x0 + x,y0 - y,color);
            setBitmapPixel(bitmap,x0 - x,y0 + y,color);
            setBitmapPixel(bitmap,x0 - x,y0 - y,color);

            error = 2 * (delta + y) - 1;
            if((delta < 0) && (error <= 0)){
                delta += 2 * ++x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if((delta > 0) && (error > 0)){
                delta +=  1 - 2 * --y;
                continue;
            }
            x++;
            delta += 2 * (x - y);
            y--;
        }
        return this;
    }

    //################ Алгорит Брезенхема для рисования эллипса #################
    public Figure ellipse(int x, int y, int a, int b, int color, Bitmap bitmap){
        int col,row;
        long a_square,b_square,two_a_square,two_b_square,four_a_square,four_b_square,d;

        b_square=b*b;
        a_square=a*a;
        row=b;
        col=0;
        two_a_square=a_square<<1;
        four_a_square=a_square<<2;
        four_b_square=b_square<<2;
        two_b_square=b_square<<1;

        d=two_a_square*((row-1)*(row))+a_square+two_b_square*(1-a_square);

        while(a_square*(row)>b_square*(col)){
            setBitmapPixel(bitmap,col+x,row+y,color);
            setBitmapPixel(bitmap,col+x,y-row,color);
            setBitmapPixel(bitmap,x-col,row+y,color);
            setBitmapPixel(bitmap,x-col,y-row,color);

            pixels.add(new Point2D(col+x,row+y));
            pixels.add(new Point2D(col+x,y-row));
            pixels.add(new Point2D(x-col,row+y));
            pixels.add(new Point2D(x-col,y-row));

            if (d>=0){
                row--;
                d-=four_a_square*(row);
            }
            d+=two_b_square*(3+(col<<1));
            col++;
        }
        d=two_b_square*(col+1)*col+two_a_square*(row*(row-2)+1)+(1-two_a_square)*b_square;
        while ((row) + 1!=0){
            setBitmapPixel(bitmap,col+x,row+y,color);
            setBitmapPixel(bitmap,col+x,y-row,color);
            setBitmapPixel(bitmap,x-col,row+y,color);
            setBitmapPixel(bitmap,x-col,y-row,color);

            pixels.add(new Point2D(col+x,row+y));
            pixels.add(new Point2D(col+x,y-row));
            pixels.add(new Point2D(x-col,row+y));
            pixels.add(new Point2D(x-col,y-row));

            if (d<=0){
                col++;
                d+=four_b_square*col;
            }
            row--;
            d+=two_a_square*(3-(row <<1));
        }
        return this;
    }

    public Figure ParamAlgCircle(Bitmap bitmap){
        pixels.clear();

        int y = 0;
        double fors = R / Math.sqrt(2);
        for(int x = 0; x < fors; x++){
            y = (int)Math.round(Math.sqrt(R*R - x*x));
            pixels.add(new Point2D(x0+x,y0+y));
            pixels.add(new Point2D(x0+y,y0+x));
            pixels.add(new Point2D(x0+y,y0-x));
            pixels.add(new Point2D(x0+x,y0-y));
            pixels.add(new Point2D(x0-x,y0-y));
            pixels.add(new Point2D(x0-y,y0-x));
            pixels.add(new Point2D(x0-y,y0+x));
            pixels.add(new Point2D(x0-x,y0+y));

            setBitmapPixel(bitmap,x0+x,y0+y,color);
            setBitmapPixel(bitmap,x0+y,y0+x,color);
            setBitmapPixel(bitmap,x0+y,y0-x,color);
            setBitmapPixel(bitmap,x0+x,y0-y,color);
            setBitmapPixel(bitmap,x0-x,y0-y,color);
            setBitmapPixel(bitmap,x0-y,y0-x,color);
            setBitmapPixel(bitmap,x0-y,y0+x,color);
            setBitmapPixel(bitmap,x0-x,y0+y,color);
        }
        return this;
    }

    public Figure fillParamAlgCircle(Bitmap bitmap){
        pixels.clear();

        int y = 0;
        double fors = R / Math.sqrt(2);
        for(int x = 0; x < fors; x++){
            y = (int)Math.round(Math.sqrt(R*R - x*x));
            pixels.add(new Point2D(x0+x,y0+y));
            pixels.add(new Point2D(x0+y,y0+x));
            pixels.add(new Point2D(x0+y,y0-x));
            pixels.add(new Point2D(x0+x,y0-y));
            pixels.add(new Point2D(x0-x,y0-y));
            pixels.add(new Point2D(x0-y,y0-x));
            pixels.add(new Point2D(x0-y,y0+x));
            pixels.add(new Point2D(x0-x,y0+y));

            setBitmapPixel(bitmap,x0+x,y0+y,color);
            setBitmapPixel(bitmap,x0+y,y0+x,color);
            setBitmapPixel(bitmap,x0+y,y0-x,color);
            setBitmapPixel(bitmap,x0+x,y0-y,color);
            setBitmapPixel(bitmap,x0-x,y0-y,color);
            setBitmapPixel(bitmap,x0-y,y0-x,color);
            setBitmapPixel(bitmap,x0-y,y0+x,color);
            setBitmapPixel(bitmap,x0-y,y0+x,color);


            Drawer.getLine(x0+x,y0+y,x0-x,y0+y, Controller.colorFill,bitmap);
            Drawer.getLine(x0+y,y0+x,x0-y,y0+x, Controller.colorFill,bitmap);
            Drawer.getLine(x0+y,y0-x,x0-y,y0-x, Controller.colorFill,bitmap);
            Drawer.getLine(x0+x,y0-y,x0-x,y0-y, Controller.colorFill,bitmap);
        }
        return this;
    }

}
