package com.example.vy.trycanvas.Draw3D.Projection3D;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;


public class Projection3DObliqueAxonometric implements Projection3D {

    float B = 0, A = 0;

    @Override
    public List<Point3D> transform(List<Point3D> list, Point3D center) {

        float x, y, z;
        float bx, by, bz;
        float cosA, cosB, sinA, sinB;
        cosA = (float) Math.cos(A);
        cosB = (float) Math.cos(B);
        sinA = (float) Math.sin(A);
        sinB = (float) Math.sin(B);

        for(Point3D point: list){
            x = point.x - center.x;
            y = point.y - center.y;
            z = point.z - center.z;

            by = y * cosA - z * sinA;
            bz = y * sinA + z * cosA;

            bx = x * cosB - bz * sinB;
            bz = x * sinB + bz * cosB;

            point.x = (int) (bx + center.x);
            point.y = (int) (by + center.y);
            point.z = (int) (bz + center.z);
        }

        return list;
    }

    @Override
    public void action(Point3D vec) {
        if (vec.y < 0) {
            A += angle;
        } else {
            if (vec.y > 0) {
                A -= angle;
            }
        }
        if (vec.x < 0) {
            B += angle;
        } else {
            if (vec.x > 0) {
                B -= angle;
            }
        }
    }
}
