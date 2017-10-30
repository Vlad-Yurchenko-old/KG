package com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DRotate;

import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

/**
 * Created by W11B on 01.06.2017.
 */
public class Transform3DRotateOz extends Transform3DRotate implements Transform3D {
    @Override
    public void transform(List<Point3D> list, Point3D vec, Point3D center) {
        float angleZ;

        if (vec.y < 0) {
            angleZ = angleDown;
        } else {
            if (vec.y > 0) {
                angleZ = angleUp;
            } else {
                angleZ = 0;
            }
        }
        float cosZ, sinZ;
        cosZ = (float) Math.cos(angleZ);
        sinZ = (float) Math.sin(angleZ);
        float x,y;

        for(Point3D point: list){
            point.x -= center.x;
            point.y -= center.y;

            //вокруг oZ
            x = point.x;
            y = point.y;
            point.x = x * cosZ - y * sinZ;
            point.y = x * sinZ + y * cosZ;

            point.x += center.x;
            point.y += center.y;
        }
    }
}
