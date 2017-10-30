package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.Draw3D.Method3D.ManagerZBuffer;
import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

/**
 * Created by HomeInc on cilinder2.06.2017.
 */

public class FillMethodTriangle3D extends Figure {
    ManagerZBuffer mp;

    Point3D A = new Point3D();
    Point3D B = new Point3D();
    Point3D P = new Point3D();
    Point3D lastA = new Point3D();
    Point3D lastB = new Point3D();

    public FillMethodTriangle3D(ManagerZBuffer mp) {
        this.mp = mp;
    }

    void triangle(Point3D t0, Point3D t1, Point3D t2, Bitmap bitmap, int color) {
        if (t0.y==t1.y && t0.y==t2.y) return; // i dont care about degenerate triangles
        if (t0.y > t1.y) {
            Point3D temp = t0;
            t0 = t1;
            t1 = temp;
        }
        if (t0.y > t2.y) {
            Point3D temp = t0;
            t0 = t2;
            t2 = temp;
        }
        if (t1.y > t2.y) {
            Point3D temp = t1;
            t1 = t2;
            t2 = temp;
        }
        int total_height = (int) (t2.y-t0.y);
        for (int i=0; i<total_height; i++) {
            boolean second_half = i>t1.y-t0.y || t1.y==t0.y;
            int segment_height = (int) (second_half ? t2.y-t1.y : t1.y-t0.y);
            float alpha = (float)i/total_height;
            float beta  = (i-(second_half ? t1.y-t0.y : 0))/segment_height; // be careful: with above conditions no division by zero here
            Point3D A = new Point3D(t0.x + alpha*(t2.x - t0.x),t0.y + alpha*(t2.y - t0.y),t0.z + alpha*(t2.z - t0.z));
            Point3D B;
            if(second_half){
                B = new Point3D(t1.x + beta*(t2.x - t1.x),t1.y + beta*(t2.y - t1.y),t1.z + beta*(t2.z - t1.z));
            } else {
                B = new Point3D(t0.x + beta*(t1.x - t0.x),t0.y + beta*(t1.y - t0.y),t0.z + beta*(t1.z - t0.z));
            }
            if (A.x > B.x){
                Point3D temp = A;
                A = B;
                B = temp;
            }
            for (int j = (int) A.x; j<=B.x; j++) {
                float phi = B.x==A.x ? (float) 1. : (j-A.x)/(B.x-A.x);
                Point3D P = new Point3D(A.x + (B.x-A.x)*phi,A.y + (B.y-A.y)*phi,A.z + (B.z-A.z)*phi);
                //int idx = (int) (P.x+P.y*width);
                mp.setPixel(bitmap,(int)P.x,(int)P.y,(int)P.z,color);
                //bitmap.setPixel((int)P.x,(int)P.y,color);
            }
        }
    }

/*
    void triangle(Point3D t0, Point3D t1, Point3D t2, Bitmap bitmap, int color) {
        if (t0.y == t1.y && t0.y == t2.y) return; // i dont care about degenerate triangles
        // sort the vertices, t0, t1, t2 lower-to-upper (bubblesort yay!)
        if (t0.y > t1.y) {
            Point3D temp = t0;
            t0 = t1;
            t1 = temp;
        }
        if (t0.y > t2.y) {
            Point3D temp = t0;
            t0 = t2;
            t2 = temp;
        }
        if (t1.y > t2.y) {
            Point3D temp = t1;
            t1 = t2;
            t2 = temp;
        }
        int total_height = (int) (t2.y - t0.y);
        for (int i = 0; i < total_height; i++) {
            boolean second_half = i > t1.y - t0.y || t1.y == t0.y;
            int segment_height = (int) (second_half ? t2.y - t1.y : t1.y - t0.y);
            float alpha = (float) i / total_height;
            float beta = (i - (second_half ? t1.y - t0.y : 0)) / segment_height; // be careful: with above conditions no division by zero here
            Point3D A = new Point3D(t0.x + alpha*(t2.x - t0.x),t0.y + alpha*(t2.y - t0.y),t0.z + alpha*(t2.z - t0.z));
            Point3D B = null;
            if(second_half){
                B = new Point3D(t1.x + beta*(t2.x - t1.x),t1.y + beta*(t2.y - t1.y),t1.z + beta*(t2.z - t1.z));
            } else {
                B = new Point3D(t0.x + beta*(t1.x - t0.x),t0.y + beta*(t1.y - t0.y),t0.z + beta*(t1.z - t0.z));
            }
            if (A.x > B.x){
                Point3D temp = A;
                A = B;
                B = temp;
            }
            for (int j = (int) A.x; j <= B.x; j++) {
                bitmap.setPixel(j, (int) (t0.y + i), color); // attention, due to int casts t0.y+i != A.y
            }
        }
    }
*/

    public void draw(Bitmap bmp, Point3D t0, Point3D t1, Point3D t2, int color, int color2) {
        if (t0.y == t1.y && t0.y == t2.y) return; // i dont care about degenerate triangles
        if (t0.y > t1.y) {
            Point3D buf = t0;
            t0 = t1;
            t1 = buf;
        }
        if (t0.y > t2.y) {
            Point3D buf = t0;
            t0 = t2;
            t2 = buf;
        }
        if (t1.y > t2.y) {
            Point3D buf = t2;
            t2 = t1;
            t1 = buf;
        }
        int total_height = (int) (t2.y - t0.y);

        int z;
        int y = (int) t0.y;

        boolean isFirs = true;
        for (int i = 0; i < total_height; i++) {
            boolean second_half = i > t1.y - t0.y || t1.y == t0.y;
            float segment_height = second_half ? t2.y - t1.y : t1.y - t0.y;
            float alpha = (float) i / total_height;
            float beta = (i - (second_half ? t1.y - t0.y : 0)) / segment_height; // be careful: with above conditions no division by zero here

            A.set(t2.x - t0.x, t2.y - t0.y, t2.z - t0.z);
            A.set(A.x * alpha, A.y * alpha, A.z * alpha);
            A.set(t0.x + A.x, t0.y + A.y, t0.z + A.z);
            if (second_half) {
                B.set(t2.x - t1.x, t2.y - t1.y, t2.z - t1.z);
                B.set(B.x * beta, B.y * beta, B.z * beta);
                B.set(B.x + t1.x, B.y + t1.y, B.z + t1.z);
            } else {
                B.set(t1.x - t0.x, t1.y - t0.y, t1.z - t0.z);
                B.set(B.x * beta, B.y * beta, B.z * beta);
                B.set(B.x + t0.x, B.y + t0.y, B.z + t0.z);
            }
            if (A.x > B.x) {
                Point3D buf = A;
                A = B;
                B = buf;
            }

            if (isFirs || i + 1 == total_height) {
                lastA.set(A);
                lastB.set(B);
                isFirs = false;
                float phi;
                for (int j = (int) A.x; j <= B.x; j++) {
                    phi = B.x == A.x ? 1.F : (j - A.x) / (B.x - A.x);
                    z = (int) (A.z + (B.z - A.z) * phi);
                    mp.setPixel(bmp, j, y + i, z, color2);
                    //bmp.setPixel(j, y + i, color2);
                }
            } else {
                Point3D l = A, lA = lastA, rA = lastB, r = B;
                if (A.x > lastA.x) {
                    l = lastA;
                    lA = A;
                }
                if (lastB.x > B.x) {
                    rA = B;
                    r = lastB;
                }
                for (int j = (int) l.x; j <= lA.x; j++) {
                    //bmp.setPixel(j, y + i, color2);
                    z = computeZ(A,B,j);
                    mp.setPixel(bmp, j, y + i, z, color2);
                }

                for (int j = (int) lA.x + 1; j < (int) rA.x; j++) {
                    //bmp.setPixel(j, y + i, color2);
                    z = computeZ(A,B,j);
                    mp.setPixel(bmp, j, y + i, z, color);
                }

                for (int j = (int) rA.x; j <= r.x; j++) {
                    //bmp.setPixel(j, y + i, color2);
                    z = computeZ(A,B,j);
                    mp.setPixel(bmp, j, y + i, z, color2);
                }
            }
            lastA.set(A);
            lastB.set(B);
        }
    }
    private int computeZ(Point3D A,Point3D B,int j){
        float phi = B.x == A.x ? 1.F : (j - A.x) / (B.x - A.x);
        return (int) (A.z + (B.z-A.z)*phi);
    }
}
