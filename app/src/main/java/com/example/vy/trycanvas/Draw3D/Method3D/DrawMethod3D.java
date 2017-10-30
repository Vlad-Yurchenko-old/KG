package com.example.vy.trycanvas.Draw3D.Method3D;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

public interface DrawMethod3D {
    void draw(Bitmap bmp, List<Point3D> list, int color, int color2);
}
