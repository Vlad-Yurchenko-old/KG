package com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DRotate;

import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;


/**
 * Created by W11B on 01.06.2017.
 */
public class Transform3DRotateOx  extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {
        float angleX;

        if (vec.y < 0) {
            angleX = angleUp;
        } else {
            if (vec.y > 0) {
                angleX = angleDown;
            } else {
                angleX = 0;
            }
        }
        float cosX, sinX;
        cosX = (float) Math.cos(angleX);
        sinX = (float) Math.sin(angleX);
        float y,z;
        for (Point3D point : list) {
            point.y -= center.y;
            point.z -= center.z;

            //вокруг oX
            y = point.y;
            z = point.z;
            point.y = y * cosX - z * sinX;
            point.z = y * sinX + z * cosX;

            point.y += center.y;
            point.z += center.z;
        }
    }
}
