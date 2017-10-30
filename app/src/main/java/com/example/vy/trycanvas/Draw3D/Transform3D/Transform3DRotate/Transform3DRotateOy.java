package com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DRotate;

import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

/**
 * Created by W11B on 01.06.2017.
 */
public class Transform3DRotateOy  extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {
        float angleY;
        if (vec.x < 0) {
            angleY = angleUp;
        } else {
            if (vec.x > 0) {
                angleY = angleDown;
            } else {
                angleY = 0;
            }
        }
        float cosY, sinY;
        cosY = (float) Math.cos(angleY);
        sinY = (float) Math.sin(angleY);
        float x,z;

        for( Point3D point : list){
            point.x -= center.x;
            point.z -= center.z;

            //вокруг oY
            x = point.x;
            z = point.z;
            point.x = x * cosY - z * sinY;
            point.z = x * sinY + z * cosY;

            point.x += center.x;
            point.z += center.z;
        }
    }
}
