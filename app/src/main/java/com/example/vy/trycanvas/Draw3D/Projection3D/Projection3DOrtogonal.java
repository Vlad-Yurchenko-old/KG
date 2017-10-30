package com.example.vy.trycanvas.Draw3D.Projection3D;

import android.graphics.Point;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

/**
 * Created by HomeInc on cilinder2.06.2017.
 */

public class Projection3DOrtogonal implements Projection3D {
    @Override
    public List<Point3D> transform(List<Point3D> list, Point3D center) {
        return list;
    }

    @Override
    public void action(Point3D vec) {

    }
}
