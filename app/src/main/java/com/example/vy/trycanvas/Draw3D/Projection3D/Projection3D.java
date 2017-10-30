package com.example.vy.trycanvas.Draw3D.Projection3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

/**
 * Created by HomeInc on cilinder2.06.2017.
 */

public interface Projection3D {
    float angle = 0.05F;
    List<Point3D> transform(List<Point3D> list, Point3D center);
    void action(Point3D vec);
}
