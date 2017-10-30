package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.figures.Figure;

/**
 * Класс для формирования фигуры прямоугольник
*   x1,x2,y1,y2 - координаты вершин главной диагонали
* */
public class FigureRect extends Figure {

    final String LOG_TAG = "VY_LOGS";

    private int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    public FigureRect(int x1, int x2, int y1, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


    /**
    * Прямоугольник
    * */
    public Figure buildRect(Bitmap bitmap){
        pixels.addAll(Drawer.getLine(x1,y1,x2,y1,color,bitmap).getFigure());
        pixels.addAll(Drawer.getLine(x1,y1,x1,y2,color,bitmap).getFigure());
        pixels.addAll(Drawer.getLine(x1,y2,x2,y2,color,bitmap).getFigure());
        pixels.addAll(Drawer.getLine(x2,y1,x2,y2,color,bitmap).getFigure());
        return this;
    }


    /**
    * Закрашенный прямоугольник
    * */
    public Figure buildFillRect(Bitmap bitmap){
        pixels.addAll(Drawer.getRectangle(x1,y1,x2,y2,color,bitmap).getFigure());

        if(x1>x2){
            int temp = x2;
            x2 = x1;
            x1 = temp;
        }
        if(y1>y2){
            int temp = y2;
            y2 = y1;
            y1 = temp;
        }

        for(int i = x1+1; i < x2; i++){
            for(int j = y1+1; j < y2; j++){
                //TODO: сильно замедляет работу
                //pixels.add(new Point2D(i,j));
                setBitmapPixel(bitmap,i,j,colorFill);
            }
        }
        return this;
    }

}
