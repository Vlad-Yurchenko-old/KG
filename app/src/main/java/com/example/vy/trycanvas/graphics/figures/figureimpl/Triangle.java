package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

public class Triangle {

    Point3D p1;
    Point3D p2;
    Point3D p3;

    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point3D[] getPointsMas() {
        return new Point3D[]{p1, p2, p3};
    }

    public Point3D getP1() {
        return p1;
    }

    public void setP1(Point3D p1) {
        this.p1 = p1;
    }

    public Point3D getP2() {
        return p2;
    }

    public void setP2(Point3D p2) {
        this.p2 = p2;
    }

    public Point3D getP3() {
        return p3;
    }

    public void setP3(Point3D p3) {
        this.p3 = p3;
    }

    public void draw(Bitmap bitmap, int color){
        Drawer.getLightLine((int)p1.getX(),(int)p1.getY(), (int)p2.getX(),(int)p2.getY(), color, bitmap);
        Drawer.getLightLine((int)p2.getX(),(int)p2.getY(), (int)p3.getX(),(int)p3.getY(), color, bitmap);
        Drawer.getLightLine((int)p3.getX(),(int)p3.getY(), (int)p1.getX(),(int)p1.getY(), color, bitmap);
    }

    public static void draw(Point3D p1, Point3D p2, Point3D p3, Bitmap bitmap, int color){
        Drawer.getLightLine((int)p1.getX(),(int)p1.getY(), (int)p2.getX(),(int)p2.getY(), color, bitmap);
        Drawer.getLightLine((int)p2.getX(),(int)p2.getY(), (int)p3.getX(),(int)p3.getY(), color, bitmap);
        Drawer.getLightLine((int)p3.getX(),(int)p3.getY(), (int)p1.getX(),(int)p1.getY(), color, bitmap);
    }
}
