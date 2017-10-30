package com.example.vy.trycanvas.Draw3D.Projection3D;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;


public class Projection3DCenter implements Projection3D {

    int dk = 1;
    int k = -150;

    @Override
    public List<Point3D> transform(List<Point3D> list, Point3D center) {
        for(Point3D point: list){
            float k2 = k / 2.F;
            float a = point.x - center.x;
            float b = (point.z - (center.z + k)) / k2;


            point.x = (int) ((b * center.x + a) / b);

            a = point.y - center.y;

            point.y = (int) ((b * center.y + a) / b);
            point.z = (int) (point.z / b);
        }
        return list;
    }

    @Override
    public void action(Point3D vec) {
        if (vec.y < 0) {
            k += dk;
        } else {
            if (vec.y > 0) {
                k -= dk;
            }
        }
    }
}
