package com.example.vy.trycanvas.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.example.vy.trycanvas.graphics.coloring.Coloring;
import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigureCurve;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigureLine;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigurePolygon3;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigurePolygoneN;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigureRect;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigureRound;
import com.example.vy.trycanvas.graphics.pixels.MyPixel;
import com.example.vy.trycanvas.graphics.pixels.MyPixelRect;
import com.example.vy.trycanvas.graphics.pixels.Point2D;
import com.example.vy.trycanvas.graphics.smoothing.FigureLineArea;
import com.example.vy.trycanvas.graphics.smoothing.FigureLineSelect;
import com.example.vy.trycanvas.graphics.smoothing.FigureLineVy;

import java.util.ArrayList;
import java.util.Random;

/**
 * Класс предназначен для отрисовки примитивов и возврата соответствующих им фигур
 *
* */

public class Drawer {

    public static MyPixel getPixel(int x, int y, int color, int size, Bitmap bitmap){
        return new MyPixelRect(size,x,y,color,bitmap);
    }

    public static Figure getLine(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureLine)
                new FigureLine(x1,y1,x2,y2)
                        .setColor(color))
                        .buildBresenLine(bitmap);
    }

    public static void getLightLine(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        ((FigureLine) new FigureLine(x1,y1,x2,y2).setColor(color)).buildParamLineLight(bitmap);
    }

    public static Figure getLineArea(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureLineArea)
                new FigureLineArea()
                        .setColor(color))
                        .draw(bitmap, new int[]{x1,y1,x2,y2});
    }

    public static Figure getLineSelect(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureLineSelect)
                new FigureLineSelect()
                        .setColor(color))
                .draw(bitmap, new int[]{x1,y1,x2,y2});
    }

    public static Figure getLineVy(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureLineVy)
                new FigureLineVy()
                        .setColor(color))
                .draw(bitmap, new int[]{x1,y1,x2,y2});
    }

    public static Figure getRound(int x, int y, int r, int color, Bitmap bitmap) {
        return ((FigureRound)
                new FigureRound(x,y,r)
                        .setColor(color))
                        .ParamAlgCircle(bitmap);
    }

    public static Figure getEllipse(int x1, int y1, int x2, int y2, int color, Bitmap bitmap){
        int a,b,x,y;
        if(x2 < x1){
            int temp = x2;
            x2 = x1;
            x1 = temp;
        }
        if(y2 < y1){
            int temp = y2;
            y2 = y1;
            y1 = temp;
        }
        x = x1+(x2-x1)/2;
        y = y1+(y2-y1)/2;
        a = x2 - x;
        b = y2 - y;
        return ((FigureRound)
                new FigureRound(1,1,1)
                        .setColor(color))
                        .ellipse(x,y,a,b,color,bitmap);
    }

    public static Figure getRectangle(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureRect)
                new FigureRect(x1, x2, y1, y2)
                        .setColor(color))
                        .buildRect(bitmap);
    }

    public static Figure getFillRectangle(int x1, int y1, int x2, int y2, int color, int colorFill, Bitmap bitmap){
        return ((FigureRect)
                new FigureRect(x1, x2, y1, y2)
                        .setColor(color)
                        .setColorFill(colorFill))
                        .buildFillRect(bitmap);
    }

    public static void getMozaik(Bitmap bitmap, int pixelSize){
        int w = bitmap.getWidth() - pixelSize/2;
        int h = bitmap.getHeight() - pixelSize/2;
        Random random = new Random();
        int color;
        for (int i = pixelSize/2; i < h; i += pixelSize) {
            for (int j = pixelSize/2; j < w; j += pixelSize) {
                color = random.nextInt() % Color.rgb(125,125,125) + Color.rgb(125,125,125);
                new MyPixelRect(pixelSize, j, i, color, bitmap);
            }
        }
    }

    public static Figure getBezierLine(int [] points, int color, Bitmap bitmap){
        return ((FigureCurve)
                new FigureCurve()
                        .setColor(color))
                        .getBezierLine(points, bitmap);
    }

    public static Figure getPolygon3(int x1, int x2, int x3, int y1, int y2, int y3, int color, int colorFill, Bitmap bitmap){
        return ((FigurePolygon3)
                new FigurePolygon3(x1, x2, x3, y1, y2, y3)
                        .setColor(color)
                        .setColorFill(colorFill))
                        .buildPolygon(bitmap);
    }

    public static Figure getTriangle(int x1, int y1, int x2, int y2, int color, Bitmap bitmap){
        return ((FigurePolygon3)
                new FigurePolygon3(x1, x2, (x2-x1)/2+x1, y1, y1, y2)
                        .setColor(color))
                        .buildPolygon(bitmap);
    }

    public static Figure getFillPolygonNGrad(int [] points, Bitmap bitmap, int colorLine, int colorFill) {
        return ((FigurePolygoneN)
                new FigurePolygoneN()
                        .setColor(colorLine)
                        .setColorFill(colorFill))
                        .draw(points, bitmap);
    }

    public static Figure getFillPolygonNStatic(int [] points, Bitmap bitmap, int colorLine, int colorFill){
        return ((FigurePolygoneN)
                new FigurePolygoneN()
                        .setColor(colorLine)
                        .setColorFill(colorFill))
                        .drawStaticColor(points, bitmap) ;
    }

    public static ArrayList<Point2D> fillFigureZatrav(int x, int y, int colorFill, Bitmap bitmap){
        //return new Coloring().paintOverZatrav(x,y,colorFill,bitmap);
        new Coloring().draw(bitmap,new Point2D(x,y),colorFill);
        return null;
    }

    /*
    *
    public static Figure getErmit(int [] points, Bitmap bitmap, int colorLine){
        return ((FigureErmit)
                new FigureErmit()
                        .setColor(colorLine))
                        .draw(points, bitmap);
    }
    public static Figure getNURBS(int [] points, Bitmap bitmap, int colorLine){
        return ((FiguresNURBS)
                new FiguresNURBS(3)
                        .setColor(colorLine))
                        .draw(points, bitmap);
    }
    public static Figure getBSpline(int [] points, Bitmap bitmap, int colorLine){
        return ((FigureBSpline)
                new FigureBSpline(2)
                        .setColor(colorLine))
                        .draw(points, bitmap);
    }
    * */

}
