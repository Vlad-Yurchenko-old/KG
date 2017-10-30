package com.example.vy.trycanvas.Draw3D.Transform3D;

import android.graphics.Point;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

/**
 * Created by HomeInc on cilinder2.06.2017.
 */

public interface Transform3D {
    void transform(List<Point3D> list, Point3D vec, Point3D center);

}
