package com.example.vy.trycanvas.Draw3D.Transform3D;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

public class Transform3DScale implements Transform3D {

    float k = 0.05F;
    float kUp = 1 + k;
    float kDown = 1 - k;

    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {

        float kX, kY;
        if (vec.x < 0) {
            kX = kDown;
        } else {
            if (vec.x > 0) {
                kX = kUp;
            } else {
                kX = 1;
            }
        }

        if (vec.y < 0) {
            kY = kUp;
        } else {
            if (vec.y > 0) {
                kY = kDown;
            } else {
                kY = 1;
            }
        }

        for (Point3D point : list) {
            point.x -= center.x;
            point.y -= center.y;
            point.z -= center.z;

            point.x *= kX;
            point.y *= kY;

            point.x += center.x;
            point.y += center.y;
            point.z += center.z;
        }

    }
}
