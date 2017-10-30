package com.example.vy.trycanvas.Draw3D;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import com.example.vy.trycanvas.Draw3D.Method3D.DrawMethod3D;
import com.example.vy.trycanvas.Draw3D.Projection3D.Projection3D;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3D;
import com.example.vy.trycanvas.graphics.figures.figureimpl.Triangle;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.ArrayList;
import java.util.List;


public class Draw3DManager {

    public static boolean changeAngle = false;

    List<Point3D> list;
    DrawMethod3D dm;

    public void setTransform3D(Transform3D transform3D) {
        this.transform3D = transform3D;
    }

    public void setProjection3D(Projection3D projection3D) {
        this.projection3D = projection3D;
    }

    public void setMethod3D(DrawMethod3D drawMethod3D){
        this.dm = drawMethod3D;
    }

    public void setFigure3d(ArrayList<Triangle> list) {
        this.list = new ArrayList<>();
        for (Triangle triangle : list) {
            this.list.add(triangle.getP1());
            this.list.add(triangle.getP2());
            this.list.add(triangle.getP3());
        }
    }
    public void setFigure3dPoints(ArrayList<Point3D> list) {
        this.list = list;
    }

    Transform3D transform3D;
    Projection3D projection3D;

    public Draw3DManager() {

    }

    public void draw(Bitmap bitmap, Bitmap bitmapMotion, int colorLine, int colorFill) {
        List<Point3D> listPoint = projection3D.transform(list, center);
        dm.draw(bitmap, listPoint, colorLine, colorFill);
    }

    Point3D start = new Point3D();
    Point3D center = new Point3D();
    Point3D vec = new Point3D();
    boolean isOne = true;

    public void action(MotionEvent event) {
        int id = event.getAction();
        switch (id) {
            case MotionEvent.ACTION_DOWN:
                if (isOne) {
                    center.x = event.getX();
                    center.y = event.getY();
                    for (Point3D point:list){
                        point.x+=center.x;
                        point.y+=center.y;
                    }
                    float minZ = (int) list.get(0).z;
                    float maxZ = (int) list.get(0).z;
                    float minX = (int) list.get(0).x;
                    float maxX = (int) list.get(0).x;
                    float minY = (int) list.get(0).y;
                    float maxY = (int) list.get(0).y;
                    for (int i = 1; i < list.size(); i++) {
                        Point3D point = list.get(i);
                        if (point.getZ() < minZ) {
                            minZ = point.getZ();
                        } else {
                            if (point.getZ() > maxZ) {
                                maxZ = point.getZ();
                            }
                        }
                        if (point.getX() < minX) {
                            minX = point.getX();
                        } else {
                            if (point.getX() > maxX) {
                                maxX = point.getX();
                            }
                        }
                        if (point.getY() < minY) {
                            minY = point.getY();
                        } else {
                            if (point.getY() > maxY) {
                                maxY = point.getY();
                            }
                        }
                    }

                    center.z = minZ + (maxZ - minZ) / 2;
                    center.x = minX + (maxX - minX) / 2;
                    center.y = minY + (maxY - minY) / 2;
                    isOne = false;
                }
                start.x = (int) event.getX();
                start.y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                vec.x = x - start.x;
                vec.y = y - start.y;
                start.x = x;
                start.y = y;
                if (projection3D != null && changeAngle) {
                    projection3D.action(vec);
                }
                if (transform3D != null) {
                    transform3D.transform(list, vec, center);
                }
                break;
        }
    }


}
