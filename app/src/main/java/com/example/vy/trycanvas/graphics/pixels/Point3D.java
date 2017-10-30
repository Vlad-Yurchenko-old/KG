package com.example.vy.trycanvas.graphics.pixels;

public class Point3D {

    public float x;
    public float y;
    public float z;

    public Point3D() {
    }

    public Point3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Point3D clone() {
        return new Point3D(x, y, z);
    }
    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void set(Point3D p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }
}
