package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

/**
* Класс для формирования фигуры линия
 * x1, y1 - координаты начала линии
 * x2, y2 - координаты конца линии
* */
public class FigureLine extends Figure {

    private int x1,x2,y1,y2;

    public FigureLine(int x1,int y1, int x2, int y2){
        if(x1 < x2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }else{
            this.x1 = x2;
            this.x2 = x1;
            this.y1 = y2;
            this.y2 = y1;
        }
    }

    /**
    * Параметрический алгоритм построения линии
    * */
    public Figure buildParamLine(Bitmap bitmap){
        pixels.clear();
        double x = x1, y = y1;
        setBitmapPixel(bitmap,(int)x, (int)y,color);
        pixels.add(new Point2D((int)x,(int)y));
        int dx = x2-x1;
        int dy = y2-y1;
        double t = 1.0 / Math.max(Math.abs(dx),Math.abs(dy));
        while(x < x2){
            x+= t*dx;
            y+= t*dy;
            setBitmapPixel(bitmap,(int)x, (int)y,color);
            pixels.add(new Point2D((int)x,(int)y));
        }
        return this;
    }
    public void buildParamLineLight(Bitmap bitmap){
        double x = x1, y = y1;
        setBitmapPixel(bitmap,(int)x, (int)y,color);
        int dx = x2-x1;
        int dy = y2-y1;
        double t = 1.0 / Math.max(Math.abs(dx),Math.abs(dy));
        while(x < x2){
            x+= t*dx;
            y+= t*dy;
            setBitmapPixel(bitmap,(int)x, (int)y,color);
        }
    }

    /**
    * Алгоритм Брезенхема построения линии
    * */
    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }
    public Figure buildBresenLine (Bitmap bitmap){

        pixels.clear();

        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = x2 - x1;//проекция на ось икс
        dy = y2 - y1;//проекция на ось игрек
        incx = sign(dx);
        incy = sign(dy);


        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;

        if (dx > dy){
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;
        }
        else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;//тогда в цикле будем двигаться по y
        }

        x = x1;
        y = y1;
        err = el/2;
        pixels.add(new Point2D(x,y));//ставим первую точку
        setBitmapPixel(bitmap,x,y,color);

        for (int t = 0; t < el; t++){
            err -= es;
            if (err < 0){
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }else{
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }
            pixels.add(new Point2D(x,y));
            setBitmapPixel(bitmap,x,y,color);
        }
        return this;
    }


}
