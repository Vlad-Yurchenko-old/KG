package com.example.vy.trycanvas.graphics.coloring;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

public class Coloring {

    final String LOG_TAG = "VY_LOGS";
    ArrayList<Point2D> pixels = new ArrayList<>();

    public void draw(Bitmap bmp, Point2D point, int color) {
        int x, y, bufXL, bufXR;
        ArrayDeque<Integer> qX = new ArrayDeque<Integer>();
        ArrayDeque<Integer> qY = new ArrayDeque<Integer>();

        int bufColor = bmp.getPixel(point.getX(), point.getY());
        if (color == bufColor) return;

        qX.push(point.getX());
        qY.push(point.getY());

        while (!qX.isEmpty()) {
            x = qX.pop();
            y = qY.pop();

            if (check(bmp, x, y))
                bmp.setPixel(x, y, color);

            //проход вправо
            bufXR = x + 1;
            while (check(bmp, bufXR, y) && bmp.getPixel(bufXR, y) == bufColor) {
                bmp.setPixel(bufXR, y, color);
                bufXR++;
            }

            //проход влево
            bufXL = x - 1;
            while (check(bmp, bufXL, y) && bmp.getPixel(bufXL, y) == bufColor) {
                bmp.setPixel(bufXL, y, color);
                bufXL--;
            }

            //ниже
            y = y - 1;
            int j = 0;
            while (j < 2) {
                for (int i = bufXL + 1; i < bufXR; i++) {
                    if (check(bmp, i, y)) {
                        if (bmp.getPixel(i, y) == bufColor) {
                            if (check(bmp, i + 1, y)) {
                                if (bmp.getPixel(i + 1, y) != bufColor || i + 1 == bufXR) {
                                    qX.push(i);
                                    qY.push(y);
                                }
                            } else {
                                if (i + 1 == bufXR) {
                                    qX.push(i);
                                    qY.push(y);
                                }
                            }
                        }
                    }
                }
                j++;
                y+=2;
            }
        }
    }

    private boolean check(Bitmap bitmap, int x, int y){
        if(x < 0 || y < 0){
            return false;
        }
        if(x >= bitmap.getWidth() || y >= bitmap.getHeight()){
            return false;
        }
        return true;
    }

    public ArrayList<Point2D> paintOverZatrav(int x, int y, int colorFill, Bitmap bmp){

        int colorStart = bmp.getPixel(x,y);

        Stack<Point2D> stack = new Stack<>();
        stack.push(new Point2D(x,y));
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        while (stack.size()!=0) {
            Point2D point2D = stack.pop();
            x = point2D.getX();
            y = point2D.getY();

            int rowCount = 0;

            int tempX = x, tempY = y;
            //холостой пробег влево
            while (bmp.getPixel(tempX, tempY) == 0 && tempX>0) {
                //pixels.add(new Point2D(tempX, tempY));
                bmp.setPixel(tempX, tempY, colorFill);
                tempX--;
                rowCount++;
            }
            tempX = x + 1;
            //холостой пробег вправо
            while (tempX<width-1 && bmp.getPixel(tempX, tempY) == colorStart) {
                //pixels.add(new Point2D(tempX, tempY));
                bmp.setPixel(tempX, tempY, colorFill);
                tempX++;
                rowCount++;
            }
            //находим затр пиксели
            tempY = y - 1;
            int tempY2 = y + 1;
            if(y>1 && y < height-1){
                boolean is1 = false, is2 = false;
                int i = 0;
                while (i < rowCount) {
                    tempX--;
                    i++;
                    if (!is1 && bmp.getPixel(tempX, tempY) == colorStart) {
                        stack.push(new Point2D(tempX, tempY));
                        is1 = true;
                        if (is2) {
                            break;
                        }
                    }
                    if (!is2 && bmp.getPixel(tempX, tempY2) == colorStart) {
                        is2 = true;
                        stack.push(new Point2D(tempX, tempY2));
                        if (is1) {
                            break;
                        }
                    }

                }
            }

        }
        return pixels;
    }


}
