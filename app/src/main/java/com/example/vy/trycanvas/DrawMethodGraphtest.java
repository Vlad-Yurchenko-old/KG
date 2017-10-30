package com.example.vy.trycanvas;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DrawMethodGraphtest {
    int w = 100;
    int buffer[] = new int[100];

    ArrayList<Point3D> list = new ArrayList<>();
    Compare my = new Compare();

    public void draw(Bitmap bmp, int[] points, int color, int width, int height) {

        list = new ArrayList<>();
        for (int i = 0; i < points.length; ) {
            Point3D p = new Point3D();
            p.x = points[i++];
            p.y = points[i++];
            p.z = points[i++];
            list.add(p);
        }
        Collections.sort(list, my);

        for(int i=0;i<buffer.length;i++){
            buffer[i] = Integer.MIN_VALUE;
        }

        for (Point3D p : list) {
            int x = (int) p.x;
            int y = (int )p.y;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                if (height != w) {
                    buffer = new int[width];
                    w = width;
                }
                if (buffer[x] < y) {
                    buffer[x] = y;
                    bmp.setPixel(x+400, y+400, color);
                }
            }
        }
    }

    class Compare implements Comparator<Point3D> {
        @Override
        public int compare(Point3D o1, Point3D o2) {
            int dz = (int) (o1.z - o2.z);
            if (dz == 0) {
                return (int) (o1.x - o2.x);
            }
            return dz;
        }
    }
}
