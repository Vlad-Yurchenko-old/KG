package com.example.vy.trycanvas.Draw3D.Method3D;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DrawMethodGraph implements DrawMethod3D{
    int w = 100;
    int buffer[] = new int[100];

    Compare my = new Compare();

    public void draw(Bitmap bmp, List<Point3D> list, int color, int color2) {
        //Collections.sort(list, my);

        for(int i=0;i<buffer.length;i++){
            buffer[i] = Integer.MIN_VALUE;
        }
        if (Controller.height != w) {
            buffer = new int[Controller.height];
            w = Controller.height;
        }
        for (Point3D p : list) {
            int x = (int) p.x;
            int y = (int )p.y;
            if (x >= 0 && x < Controller.width && y >= 0 && y < Controller.height) {
                if (buffer[x] < y) {
                    buffer[x] = y;
                    bmp.setPixel(x, y, color);
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
