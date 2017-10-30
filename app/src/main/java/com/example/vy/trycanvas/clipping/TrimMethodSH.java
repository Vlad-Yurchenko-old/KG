package com.example.vy.trycanvas.clipping;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.pixels.Point2D;

import java.util.ArrayList;

public class TrimMethodSH {

    public void draw(Bitmap bmp, int[] points, int color) {
        ArrayList<Point2D> list;
        int temp[] = new int[4];

        int k = 0;

        //прорисовка окна
        initMas(k, 4, 0, 4, temp, points);
        Drawer.getRectangle(temp[0],temp[1],temp[2],temp[3], Color.RED,bmp);

        k += 4;

        //многоугольников
        int numPoints;
        while (k < points.length) {
            numPoints = points[k++];
            int size = numPoints * 2;
            if (numPoints > 1) {
                list = new ArrayList<>(numPoints);
                for (int i = k; i < k + size; i += 2) {
                    list.add(new Point2D(points[i], points[i + 1]));
                }
                list = trimX(points[0], false, list);
                list = trimX(points[2], true, list);
                list = trimY(points[1], false, list);
                list = trimY(points[3], true, list);
                int result[] = new int[list.size() * 2];
                int h = 0;
                for (Point2D p : list) {
                    result[h++] = p.x;
                    result[h++] = p.y;
                }
                if (result.length != 0) {
                    for(int i = 0; i < result.length; i+=2){
                        int j = (i+2)%result.length;
                        Drawer.getLine(result[i],result[i+1],result[j],result[j+1],color,bmp);
                    }
                }
            }
            k += size;
        }

    }

    private void initMas(int startPos, int size, int n, int m, int[] temp, int[] points) {
        for (int i = n, k = 0; i < m; i++, k++) {
            int var = (i - startPos) % size;
            temp[k] = points[startPos + var];
        }
    }

    private ArrayList<Point2D> trimX(int x, boolean isLeft, ArrayList<Point2D> list) {
        ArrayList<Point2D> result = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int pre = (i + size - 1) % size;
            Point2D p1 = list.get(pre);
            Point2D p2 = list.get(i);
            if (x < p1.x ^ isLeft) {// первая точка в окне
                if (x < p2.x ^ isLeft) {//вторая точка в окне
                    result.add(p2);
                } else {//вторая точка вне окна
                    Point2D p3 = new Point2D();
                    p3.x = x;
                    p3.y = p2.y + (int) ((x - p2.x) * ((float) (p1.y - p2.y) / (p1.x - p2.x)));
                    result.add(p3);
                }
            } else {// первая точка вне окна
                if (x < p2.x ^ isLeft) {//вторая точка в окне
                    Point2D p3 = new Point2D();
                    p3.x = x;
                    p3.y = p1.y + (int) ((x - p1.x) * ((float) (p2.y - p1.y) / (p2.x - p1.x)));
                    result.add(p3);
                    result.add(p2);
                }
            }
        }
        return result;
    }

    private ArrayList<Point2D> trimY(int y, boolean isTop, ArrayList<Point2D> list) {
        ArrayList<Point2D> result = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int pre = (i + size - 1) % size;
            Point2D p1 = list.get(pre);
            Point2D p2 = list.get(i);
            if (y < p1.y ^ isTop) {// первая точка в окне
                if (y < p2.y ^ isTop) {//вторая точка в окне
                    result.add(p2);
                } else {//вторая точка вне окна
                    Point2D p3 = new Point2D();
                    p3.y = y;
                    p3.x = p2.x + (int) ((y - p2.y) * ((float) (p1.x - p2.x) / (p1.y - p2.y)));
                    result.add(p3);
                }
            } else {// первая точка вне окна
                if (y < p2.y ^ isTop) {//вторая точка в окне
                    Point2D p3 = new Point2D();
                    p3.y = y;
                    p3.x = p1.x + (int) ((y - p1.y) * ((float) (p2.x - p1.x) / (p2.y - p1.y)));
                    result.add(p3);
                    result.add(p2);
                }
            }
        }
        return result;
    }
}
