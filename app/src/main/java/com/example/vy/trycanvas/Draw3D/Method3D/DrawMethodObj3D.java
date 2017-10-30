package com.example.vy.trycanvas.Draw3D.Method3D;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.figures.figureimpl.FillMethodTriangle3D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.List;

public class DrawMethodObj3D implements DrawMethod3D {
    ManagerZBuffer mp;
    FillMethodTriangle3D dm ;
    public DrawMethodObj3D(ManagerZBuffer mp){
        this.mp = mp;
        dm = new FillMethodTriangle3D(mp);
    }

    public void draw(Bitmap bmp, List<Point3D> list, int color, int color2) {
        mp.clearBufer();
        for (int i = 0; i < list.size(); i += 3) {
            dm.draw(bmp,list.get(i), list.get(i + 1), list.get(i + 2),color,color2);
            //dm.triangle(list.get(i), list.get(i + 1), list.get(i + 2),bmp,color);
        }
    }

}
