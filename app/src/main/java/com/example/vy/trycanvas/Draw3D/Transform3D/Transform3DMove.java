package com.example.vy.trycanvas.Draw3D.Transform3D;

import android.graphics.Point;
import android.util.Log;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

/**
 * Created by HomeInc on cilinder2.06.2017.
 */

public class Transform3DMove implements Transform3D {
    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {
        for (Point3D point : list) {
            point.x += vec.x;
            point.y += vec.y;
        }
    }
}
