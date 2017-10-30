package com.example.vy.trycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;

import com.example.vy.trycanvas.Draw3D.Draw3DManager;
import com.example.vy.trycanvas.clipping.TrimMethodKoen;
import com.example.vy.trycanvas.clipping.TrimMethodSH;
import com.example.vy.trycanvas.clipping.TrimMethodVA.TrimMethodVA;
import com.example.vy.trycanvas.file.FileReadBMP;
import com.example.vy.trycanvas.file.FileReader;
import com.example.vy.trycanvas.file.FileWriter;
import com.example.vy.trycanvas.file.parser.OBJParser;
import com.example.vy.trycanvas.fragments.dialogs.FragmentMenuTransformationDialog;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigurePolygon3;
import com.example.vy.trycanvas.graphics.pixels.Point2D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;
import com.example.vy.trycanvas.transformation.Reflection;
import com.example.vy.trycanvas.transformation.Scaling;
import com.example.vy.trycanvas.transformation.Transfer;
import com.example.vy.trycanvas.transformation.Turning;
import com.example.vy.trycanvas.Draw3D.Method3D.DrawMethodGraph;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Operation {

    private ArrayList<Figure> tempFigureCash;

    private ArrayList<Point2D> point2Ds;
    private ArrayList<Point2D> point2Ds2;
    private ArrayList<Point2D> motion;//пока только в безье используется
    private int x, y, m = -1, n = -1;

    private Figure currentFigure;
    private boolean kostilb = true;

    public Operation() {
        tempFigureCash = new ArrayList<>();
        point2Ds = new ArrayList<>();
        point2Ds2 = new ArrayList<>();
        motion = new ArrayList<>();
        x = -1;
    }

    public void operationClear() {
        tempFigureCash.clear();
        point2Ds.clear();
        point2Ds2.clear();
        motion.clear();
        x = -1;
        y = -1;
        m = -1;
        n = -1;
        currentFigure = null;
        kostilb = false;
    }



    public void operationTEST(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int colorLine, int colorFill, Field field, Context context) {
        /*switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (draw3D == null) {
                    OBJParser objParser = new OBJParser();
                    ArrayList<Triangle> polygon3s = objParser.parseTriangle3D(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                            field.getBitmap().getWidth()/5,
                            field.getBitmap().getHeight()/5);
                    draw3D = new Draw3DManager(polygon3s, colorLine, colorFill);
                }
                draw3D.action(event);
                break;
            case MotionEvent.ACTION_UP:
                bitmap.eraseColor(0);
                draw3D.draw(bitmap, bitmapMotion, colorLine, colorFill);
                break;
            case MotionEvent.ACTION_MOVE:
                draw3D.action(event);
                break;
        }*/

        onSet(200,200,bitmap);
    }

    int rw, lw, uh, dh, lz, rz;

    public void onSet(int width, int height, Bitmap bitmap) {

        rw = width / 2;
        lw = -rw;
        uh = height / 2;
        dh = -uh;
        lz = -height;
        rz = height;

        // int step = (int) (_pd.getScale() * 2);
        int step = 1;

        ArrayList<Point3D> list = new ArrayList<>();

        for (int k = lz; k < rz; k++) {
            for (int i = lw; i < rw; i += step) {
                for (int j = dh; j < uh; j += step) {
                    if (func(i, j, k)) {
                        Point3D p = new Point3D(i, j, k);
                        list.add(p);
                    }
                }
            }
        }

        int[] result = new int[list.size() * 3];

        int h = 0;
        for (Point3D p : list) {
            result[h++] = (int) p.x;
            result[h++] = (int) p.y;
            result[h++] = (int) p.z;
        }
        new DrawMethodGraphtest().draw(bitmap, result, Controller.colorLine,width,height);
    }

    boolean func(float x, float y, float z) {
        //return Math.abs(Math.sin(x)*Math.cos(y)-(1.5F * Math.cos(y) * Math.exp(-y)) - z) <= 5;
        return Math.abs(x * x / 50 + y * y / 50 - z) <= 5 ;
        //return (float) (Math.sin(x)*Math.cos(y)-(1.5F * Math.cos(y) * Math.exp(-y)));
        //return Math.cos(Math.sqrt(x*x+y*y))/(1+Math.sqrt(x*x+y*y)) <= 5;
    }



    /*
    *
    * Базовые операции
    *
    * */

    public void operationPen(MotionEvent event, int color, int size, Bitmap bitmap) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            Drawer.getPixel((int) event.getX(), (int) event.getY(), color, size, bitmap);
        }
    }

    public void operationLine(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getLine(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getLine(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationRound(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            int xs2 = (int) event.getX() - x;
            xs2 *= xs2;
            int ys2 = (int) event.getY() - y;
            ys2 *= ys2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getRound(x, y, (int) Math.sqrt(Math.abs(xs2 + ys2)), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getRound(x, y, (int) Math.sqrt(Math.abs(xs2 + ys2)), color, bitmapMotion);
            }
        }
    }

    public void operationEllipse(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getEllipse(x, y,
                        (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getEllipse(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationRect(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getRectangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getRectangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationBezier(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (point2Ds.size() == 0) {
            point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (point2Ds.size() == 1) {
                    point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                } else {// добавляем в предпоследнюю
                    point2Ds.add(point2Ds.size() - 1, new Point2D((int) event.getX(), (int) event.getY()));
                }

                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getBezierLine(parseArrayList(point2Ds), color, bitmapMotion));
            } else { //анимация
                motion = new ArrayList<>(point2Ds);
                motion.add(point2Ds.size() - 1, new Point2D((int) event.getX(), (int) event.getY()));
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getBezierLine(parseArrayList(motion), color, bitmapMotion);
                motion.clear();

            }
        }
    }



    /*
    *
    * Работа с файлами
    *
    * */

    public void operationSave(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        FileWriter fl = new FileWriter();
        fl.writeBMP24("test", bitmap);
    }

    public void operationOpen(Field field) {
        FileReadBMP frb = new FileReadBMP();
        field.setBitmap(frb.readBMP24("test"));
    }



    /*
    *
    * Полигоны
    *
    * */

    public void operationPolygon3(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (point2Ds.size() == 0) {
            point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
        } else {
            if (point2Ds.size() == 1) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                    field.getFigures().add(Drawer.getLine(point2Ds.get(0).getX(), point2Ds.get(0).getY(), point2Ds.get(1).getX(), point2Ds.get(1).getY(), color, bitmap));
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    Drawer.getLine(point2Ds.get(0).getX(), point2Ds.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }
            } else {
                if (point2Ds.size() == 2) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        field.getFigures().add(Drawer.getPolygon3(point2Ds.get(0).getX(), point2Ds.get(1).getX(), (int) event.getX(),
                                point2Ds.get(0).getY(), point2Ds.get(1).getY(), (int) event.getY(),
                                color, colorFill, bitmap));
                        point2Ds.clear();
                    } else {
                        bitmapMotion.eraseColor(Color.WHITE);
                        Drawer.getPolygon3(point2Ds.get(0).getX(), point2Ds.get(1).getX(), (int) event.getX(),
                                point2Ds.get(0).getY(), point2Ds.get(1).getY(), (int) event.getY(),
                                color, colorFill, bitmapMotion);
                    }
                }
            }
        }
    }

    public void operationTriangle(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public ArrayList<Point2D> operationPolygon(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int numNodes, int color, ArrayList<Point2D> point2Ds) {
        if (point2Ds == null) {
            point2Ds = this.point2Ds;
        }
        if (point2Ds.size() == 0) {
            point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                if (point2Ds.size() < numNodes) {
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmap);
                    }
                } else {
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmap);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), point2Ds.get(0).getX(), point2Ds.get(0).getY(), color, bitmap);
                }

            } else {
                if (point2Ds.size() != numNodes - 1) {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                    Drawer.getLine(point2Ds.get(0).getX(), point2Ds.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }


            }
        }
        return point2Ds;
    }



    /*
    *
    * Операции закрасок
    *
    * */

    public void operationZatrav(Bitmap bitmap, MotionEvent event, int colorFill) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawer.fillFigureZatrav((int) event.getX(), (int) event.getY(), colorFill, bitmap);
        }
    }

    public void operationFillRect(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getFillRectangle(x, y, (int) event.getX(), (int) event.getY(), color, colorFill, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getFillRectangle(x, y, (int) event.getX(), (int) event.getY(), color, colorFill, bitmapMotion));
            }
        }
    }

    public void operationFillPolygonNGrad(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field, int numNodes) {
        if (point2Ds.size() == 0) {
            point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                if (point2Ds.size() < numNodes) {
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmap);
                    }
                } else {
                    int[] pts = parseArrayList(point2Ds);
                    field.getFigures().add(Drawer.getFillPolygonNGrad(pts, bitmap, color, colorFill));
                    point2Ds.clear();
                }

            } else {
                if (point2Ds.size() != numNodes - 1) {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                    Drawer.getLine(point2Ds.get(0).getX(), point2Ds.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }


            }

        }
    }

    public void operationFillPolygonNStatic(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field, int numNodes) {
        if (point2Ds.size() == 0) {
            point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                if (point2Ds.size() < numNodes) {
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmap);
                    }
                } else {
                    int[] pts = parseArrayList(point2Ds);
                    field.getFigures().add(Drawer.getFillPolygonNStatic(pts, bitmap, color, colorFill));
                    point2Ds.clear();
                }

            } else {
                if (point2Ds.size() != numNodes - 1) {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    for (int i = 0; i < point2Ds.size() - 1; i++) {
                        Drawer.getLine(point2Ds.get(i).getX(), point2Ds.get(i).getY(), point2Ds.get(i + 1).getX(), point2Ds.get(i + 1).getY(), color, bitmapMotion);
                    }
                    Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                    Drawer.getLine(point2Ds.get(0).getX(), point2Ds.get(0).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }


            }

        }
    }



    /*
    *
    * Операции отрисовки сглаженных линий
    *
    * */

    public void operationLineArea(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getLineArea(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getLineArea(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationLineSelect(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getLineSelect(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getLineSelect(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationLineVy(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                bitmapMotion.eraseColor(Color.WHITE);
                field.getFigures().add(Drawer.getLineVy(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getLineVy(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }


    /*
    *
    * Трансформирование
    *
    * */

    public void operationSelect(MotionEvent event, Field field, Controller controller, FragmentManager fragmentManager) {
        FragmentMenuTransformationDialog fragmentMenuTransformationDialog = new FragmentMenuTransformationDialog();
        fragmentMenuTransformationDialog.setController(controller);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            boolean flag = false;
            int left = x, right = x, up = y, down = y;

            for (Figure figure : field.getFigures()) {
                if (figure.contains(x, y)) {
                    Log.d("VY_LOGS", String.valueOf("Find : " + x + "\t" + y));
                    currentFigure = figure;
                    fragmentMenuTransformationDialog.show(fragmentManager, "custom");
                    break;
                }
            }

            if (currentFigure == null) {
                while (!flag) {
                    left--;
                    right++;
                    up++;
                    down--;
                    for (Figure figure : field.getFigures()) {
                        if (figure.contains(left, y)) {
                            Log.d("VY_LOGS", String.valueOf("Find : " + x + "\t" + y));
                            currentFigure = figure;
                            fragmentMenuTransformationDialog.show(fragmentManager, "custom");
                            flag = true;
                            return;
                        }
                    }

                    for (Figure figure : field.getFigures()) {
                        if (figure.contains(right, y)) {
                            Log.d("VY_LOGS", String.valueOf("Find : " + x + "\t" + y));
                            currentFigure = figure;
                            fragmentMenuTransformationDialog.show(fragmentManager, "custom");
                            flag = true;
                            return;
                        }
                    }

                    for (Figure figure : field.getFigures()) {
                        if (figure.contains(x, up)) {
                            Log.d("VY_LOGS", String.valueOf("Find : " + x + "\t" + y));
                            currentFigure = figure;
                            fragmentMenuTransformationDialog.show(fragmentManager, "custom");
                            flag = true;
                            return;
                        }
                    }

                    for (Figure figure : field.getFigures()) {
                        if (figure.contains(x, down)) {
                            Log.d("VY_LOGS", String.valueOf("Find : " + x + "\t" + y));
                            currentFigure = figure;
                            fragmentMenuTransformationDialog.show(fragmentManager, "custom");
                            flag = true;
                            return;
                        }
                    }

                }
            }

        }
    }

    public void operationTransfer(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, Field field) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) event.getX();
            y = (int) event.getY();
            kostilb = true;
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                bitmapMotion.eraseColor(Color.WHITE);
                Transfer.transfer(bitmap, currentFigure.getFigure(), (int) event.getX() - x, (int) event.getY() - y, currentFigure.getColor(), true);
            } else {
                CopyOnWriteArrayList<Point2D> temp = new CopyOnWriteArrayList<>(currentFigure.getFigure());
                if (kostilb) {//очистка изначальной фигуры
                    kostilb = false;
                    Transfer.clearBitInFigure(bitmap, temp);
                }
                bitmapMotion.eraseColor(Color.WHITE);
                Transfer.transfer(bitmapMotion, temp, (int) event.getX() - x, (int) event.getY() - y, currentFigure.getColor(), false);
            }
        }
    }

    public void operationScaling(Bitmap bitmap, MotionEvent event, Field field) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            int temp = 0;
            if (point2Ds.size() == 0) {
                temp = (x + y) - ((int) event.getX() + (int) event.getY());
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            } else {
                temp = point2Ds.get(0).getX() + point2Ds.get(0).getY() - ((int) event.getX() + (int) event.getY());
                point2Ds.clear();
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            }
            if (temp > 0) {
                Scaling.scale(bitmap, field.getFigures(), 0.97);
            } else {
                Scaling.scale(bitmap, field.getFigures(), 1.03);
            }
        }
    }

    public void operationTurning(Bitmap bitmap, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            int temp = 0;
            if (point2Ds.size() == 0) {
                temp = (x) - ((int) event.getX());
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            } else {
                temp = point2Ds.get(0).getX() - ((int) event.getX());
                point2Ds.clear();
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            }
            if (temp > 0) {
                new Turning().transformate(bitmap, currentFigure.getFigure(), currentFigure.getColor(), 1);
            } else {
                new Turning().transformate(bitmap, currentFigure.getFigure(), currentFigure.getColor(), -1);
            }
        }
    }

    public void operationReflect(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getLine(x, y, (int) event.getX(), (int) event.getY(), Color.BLACK, bitmapMotion);
            } else {
                Drawer.getLine(x, y, (int) event.getX(), (int) event.getY(), Color.BLACK, bitmap);
                Reflection.reflect(bitmap, currentFigure.getFigure(), new Point2D(x, y), new Point2D((int) event.getX(), (int) event.getY()));
            }
        }
    }


    /*
    *
    * Отсечение
    *
    * */
    public void operationTrimKoen(Bitmap bitmap, Bitmap bitmapMorion, MotionEvent event, Field field) {
        if (point2Ds.size() == 0) {
            point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
        } else {
            if (point2Ds.size() < 8) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (point2Ds.size() < 6) {
                        bitmapMorion.eraseColor(Color.WHITE);
                        Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), Color.BLACK, bitmap);
                        point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                    } else {
                        point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                        //field.createNewBitmap();
                        int n = point2Ds.size() * 2;
                        int[] pts = new int[n];
                        pts[0] = point2Ds.get(point2Ds.size() - 2).getX();
                        pts[1] = point2Ds.get(point2Ds.size() - 2).getY();
                        pts[2] = point2Ds.get(point2Ds.size() - 1).getX();
                        pts[3] = point2Ds.get(point2Ds.size() - 1).getY();

                        int count = 4;
                        for (int i = 0; i < point2Ds.size() - 2; i++) {
                            pts[count] = point2Ds.get(i).getX();
                            count++;
                            pts[count] = point2Ds.get(i).getY();
                            count++;
                        }

                        bitmap.eraseColor(Color.WHITE);
                        TrimMethodKoen.draw(bitmap, pts);
                    }
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                    } else {
                        if (point2Ds.size() < 6) {
                            bitmapMorion.eraseColor(Color.WHITE);
                            Drawer.getLine(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), Color.BLACK, bitmapMorion);
                        } else {
                            bitmapMorion.eraseColor(Color.WHITE);
                            Drawer.getRectangle(point2Ds.get(point2Ds.size() - 1).getX(), point2Ds.get(point2Ds.size() - 1).getY(), (int) event.getX(), (int) event.getY(), Color.BLACK, bitmapMorion);
                        }

                    }
                }
            } else {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                } else {
                    Point2D point2D1 = point2Ds.get(point2Ds.size() - 2);
                    Point2D point2D2 = point2Ds.get(point2Ds.size() - 1);

                    point2Ds.remove(point2Ds.size() - 1);
                    point2Ds.remove(point2Ds.size() - 1);

                    int dx = x - (int) event.getX();
                    int dy = y - (int) event.getY();

                    point2D1.setX(point2D1.getX() - dx / 30);
                    point2D1.setY(point2D1.getY() - dy / 30);
                    point2D2.setX(point2D2.getX() - dx / 30);
                    point2D2.setY(point2D2.getY() - dy / 30);

                    point2Ds.add(point2D1);
                    point2Ds.add(point2D2);

                    int n = point2Ds.size() * 2;
                    int[] pts = new int[n];
                    pts[0] = point2Ds.get(point2Ds.size() - 2).getX();
                    pts[1] = point2Ds.get(point2Ds.size() - 2).getY();
                    pts[2] = point2Ds.get(point2Ds.size() - 1).getX();
                    pts[3] = point2Ds.get(point2Ds.size() - 1).getY();

                    int count = 4;
                    for (int i = 0; i < point2Ds.size() - 2; i++) {
                        pts[count] = point2Ds.get(i).getX();
                        count++;
                        pts[count] = point2Ds.get(i).getY();
                        count++;
                    }

                    bitmap.eraseColor(Color.WHITE);
                    TrimMethodKoen.draw(bitmap, pts);
                }


            }

        }

    }

    public void operationTrimSH(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, Field field, int numNodes, int color) {
        if (point2Ds.size() < numNodes) {
            operationPolygon(bitmap, bitmapMotion, event, numNodes, color, point2Ds);
        } else {
            if (point2Ds.size() == numNodes + 2) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                } else {
                    Point2D point2D1 = point2Ds.get(point2Ds.size() - 2);
                    Point2D point2D2 = point2Ds.get(point2Ds.size() - 1);
                    point2Ds.remove(point2Ds.size() - 1);
                    point2Ds.remove(point2Ds.size() - 1);
                    int dx = x - (int) event.getX();
                    int dy = y - (int) event.getY();
                    point2D1.setX(point2D1.getX() - dx / 30);
                    point2D1.setY(point2D1.getY() - dy / 30);
                    point2D2.setX(point2D2.getX() - dx / 30);
                    point2D2.setY(point2D2.getY() - dy / 30);
                    point2Ds.add(point2D1);
                    point2Ds.add(point2D2);
                    int[] pts = new int[point2Ds.size() * 2 + 1];
                    pts[0] = point2Ds.get(point2Ds.size() - 2).getX();
                    pts[1] = point2Ds.get(point2Ds.size() - 2).getY();
                    pts[2] = point2Ds.get(point2Ds.size() - 1).getX();
                    pts[3] = point2Ds.get(point2Ds.size() - 1).getY();
                    point2Ds.remove(point2Ds.size() - 1);
                    point2Ds.remove(point2Ds.size() - 1);
                    pts[4] = numNodes;
                    for (int i = 0, j = 5; i < point2Ds.size(); i++) {
                        pts[j] = point2Ds.get(i).getX();
                        j++;
                        pts[j] = point2Ds.get(i).getY();
                        j++;
                    }
                    point2Ds.add(new Point2D(pts[0], pts[1]));
                    point2Ds.add(new Point2D(pts[2], pts[3]));
                    bitmap.eraseColor(Color.WHITE);
                    new TrimMethodSH().draw(bitmap, pts, color);
                }
            } else {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                    point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        //field.createNewBitmap();
                        bitmap.eraseColor(Color.WHITE);

                        int[] pts = new int[point2Ds.size() * 2 + 1];
                        pts[0] = point2Ds.get(point2Ds.size() - 2).getX();
                        pts[1] = point2Ds.get(point2Ds.size() - 2).getY();
                        pts[2] = point2Ds.get(point2Ds.size() - 1).getX();
                        pts[3] = point2Ds.get(point2Ds.size() - 1).getY();
                        point2Ds.remove(point2Ds.size() - 1);
                        point2Ds.remove(point2Ds.size() - 1);
                        pts[4] = numNodes;
                        for (int i = 0, j = 5; i < point2Ds.size(); i++) {
                            pts[j] = point2Ds.get(i).getX();
                            j++;
                            pts[j] = point2Ds.get(i).getY();
                            j++;
                        }
                        point2Ds.add(new Point2D(pts[0], pts[1]));
                        point2Ds.add(new Point2D(pts[2], pts[3]));
                        new TrimMethodSH().draw(bitmap, pts, color);
                    }
                } else {
                    int n = point2Ds.size() - 1;
                    bitmapMotion.eraseColor(Color.WHITE);
                    Drawer.getRectangle(point2Ds.get(n).getX(), point2Ds.get(n).getY(), (int) event.getX(), (int) event.getY(), color, bitmapMotion);
                }
            }
        }
    }

    public void operationTrimVA(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, Field field, int numNodesArea, int numNodes, int color) {
        //new TrimMethodVA().draw(bitmap, new int[]{3, 200, 200, 400, 400, 600, 600, 3, 200, 200, 350, 230, 400, 400});
        if (point2Ds.size() < numNodes) {
            operationPolygon(bitmap, bitmapMotion, event, numNodes, color, point2Ds);
        } else {
            if (point2Ds.size() + point2Ds2.size() == numNodes + numNodesArea) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                } else {
                    int dx = x - (int) event.getX();
                    int dy = y - (int) event.getY();
                    int[] pts = new int[point2Ds.size() * 2 + point2Ds2.size() * 2 + 2];
                    pts[0] = numNodesArea;
                    for (int i = 0, j = 1; i < point2Ds2.size(); i++) {
                        point2Ds2.get(i).setX(point2Ds2.get(i).getX() - dx / 30);
                        point2Ds2.get(i).setY(point2Ds2.get(i).getY() - dy / 30);
                        pts[j] = point2Ds2.get(i).getX();
                        j++;
                        pts[j] = point2Ds2.get(i).getY();
                        j++;
                    }
                    pts[numNodesArea * 2 + 1] = numNodes;
                    for (int i = 0, j = numNodesArea * 2 + 2; i < point2Ds.size(); i++) {
                        pts[j] = point2Ds.get(i).getX();
                        j++;
                        pts[j] = point2Ds.get(i).getY();
                        j++;
                    }
                    bitmap.eraseColor(Color.WHITE);
                    new TrimMethodVA().draw(bitmap, pts);
                }
            } else {
                point2Ds2 = operationPolygon(bitmap, bitmapMotion, event, numNodes, color, point2Ds2);
                if (point2Ds.size() + point2Ds2.size() == numNodes + numNodesArea) {
                    operationTrimVA(bitmap, bitmapMotion, event, field, numNodesArea, numNodes, color);
                }
            }
        }
    }


    public void operationTrimKoenHead(Bitmap bitmap, Bitmap bitmapMorion, MotionEvent event, Field field, Context context) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (m != -1) {
                int mas[] = new int[4 + tempFigureCash.size() * 6];
                int h = 0;
                int dx = (x + m) / 2 - (int) event.getX();
                int dy = (y + n) / 2 - (int) event.getY();
                x -= dx / 30;
                y -= dy / 20;
                m -= dx / 30;
                n -= dy / 20;
                mas[h++] = x;
                mas[h++] = y;
                mas[h++] = m;
                mas[h++] = n;
                for (Figure figurePolygon3 : tempFigureCash) {
                    int[] temp = figurePolygon3.getRefPoints();
                    for (int i = 0; i < temp.length; i++) {
                        mas[h++] = temp[i];
                    }
                }
                bitmap.eraseColor(Color.WHITE);
                TrimMethodKoen.draw(bitmap, mas);
            } else {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    OBJParser objParser = new OBJParser();
                    ArrayList<FigurePolygon3> polygon3s = objParser.parseTriangles(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                            field.getBitmap().getWidth(),
                            field.getBitmap().getHeight());
                    tempFigureCash.addAll(polygon3s);
                    int mas[] = new int[4 + polygon3s.size() * 6];
                    int h = 0;
                    mas[h++] = x;
                    mas[h++] = y;
                    m = (int) event.getX();
                    n = (int) event.getY();
                    mas[h++] = m;
                    mas[h++] = n;
                    for (FigurePolygon3 figurePolygon3 : polygon3s) {
                        int[] temp = figurePolygon3.getRefPoints();
                        for (int i = 0; i < temp.length; i++) {
                            mas[h++] = temp[i];
                        }
                    }
                    bitmapMorion.eraseColor(Color.WHITE);
                    TrimMethodKoen.draw(bitmap, mas);
                } else {
                    bitmapMorion.eraseColor(Color.WHITE);
                    Drawer.getRectangle(x, y, (int) event.getX(), (int) event.getY(), Controller.colorLine, bitmapMorion);
                }
            }

        }
    }

    public void operationTrimSHHead(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, Field field, int numNodes, int color, Context context) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (m != -1) {
                int mas[] = new int[4 + tempFigureCash.size() * 7];
                int h = 0;
                int dx = (x + m) / 2 - (int) event.getX();
                int dy = (y + n) / 2 - (int) event.getY();
                x -= dx / 30;
                y -= dy / 20;
                m -= dx / 30;
                n -= dy / 20;
                mas[h++] = x;
                mas[h++] = y;
                mas[h++] = m;
                mas[h++] = n;
                for (Figure figurePolygon3 : tempFigureCash) {
                    mas[h++] = 3;
                    int[] temp = figurePolygon3.getRefPoints();
                    for (int i = 0; i < temp.length; i++) {
                        mas[h++] = temp[i];
                    }
                }
                bitmap.eraseColor(Color.WHITE);
                new TrimMethodSH().draw(bitmap, mas, color);
            } else {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    OBJParser objParser = new OBJParser();
                    ArrayList<FigurePolygon3> polygon3s = objParser.parseTriangles(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                            field.getBitmap().getWidth(),
                            field.getBitmap().getHeight());
                    tempFigureCash.addAll(polygon3s);
                    int mas[] = new int[4 + tempFigureCash.size() * 7];
                    int h = 0;
                    mas[h++] = x;
                    mas[h++] = y;
                    m = (int) event.getX();
                    n = (int) event.getY();
                    mas[h++] = m;
                    mas[h++] = n;
                    for (FigurePolygon3 figurePolygon3 : polygon3s) {
                        mas[h++] = 3;
                        int[] temp = figurePolygon3.getRefPoints();
                        for (int i = 0; i < temp.length; i++) {
                            mas[h++] = temp[i];
                        }
                    }
                    bitmapMotion.eraseColor(Color.WHITE);
                    new TrimMethodSH().draw(bitmap, mas, color);
                } else {
                    bitmapMotion.eraseColor(Color.WHITE);
                    Drawer.getRectangle(x, y, (int) event.getX(), (int) event.getY(), Controller.colorLine, bitmapMotion);
                }
            }
        }
    }

    public void operationTrimVAHead(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, Field field, int numNodesArea, int color, Context context) {
        //new TrimMethodVA().draw(bitmap, new int[]{3, 200, 200, 400, 400, 600, 600, 3, 200, 200, 350, 230, 400, 400});
        if (point2Ds.size() < numNodesArea) {
            operationPolygon(bitmap, bitmapMotion, event, numNodesArea, color, point2Ds);
        } else {

            if (tempFigureCash.size() == 0) {
                OBJParser objParser = new OBJParser();
                ArrayList<FigurePolygon3> polygon3s = objParser.parseTriangles(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                        field.getBitmap().getWidth(),
                        field.getBitmap().getHeight());
                tempFigureCash.addAll(polygon3s);
            }

            int h = 0;
            int[] pts = new int[point2Ds.size() * 2 + tempFigureCash.size() * 7 + 1];
            pts[h++] = numNodesArea;
            for (int i = 0; i < point2Ds.size(); i++) {
                pts[h++] = point2Ds.get(i).getX();
                pts[h++] = point2Ds.get(i).getY();
            }
            for (int i = 0; i < tempFigureCash.size(); i++) {
                pts[h++] = 3;
                int[] temp = tempFigureCash.get(i).getRefPoints();
                for (int j = 0; j < temp.length; j++) {
                    pts[h++] = temp[j];
                }
            }
            bitmap.eraseColor(Color.WHITE);
            new TrimMethodVA().drawForHead(bitmap, pts);
        }
    }


    public void operationProjection(Draw3DManager draw3DManager, Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int colorLine, int colorFill, Field field, Context context){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                draw3DManager.action(event);
                break;
            case MotionEvent.ACTION_UP:
                bitmap.eraseColor(0);
                draw3DManager.draw(bitmap, bitmapMotion, colorLine, colorFill);
                break;
            case MotionEvent.ACTION_MOVE:
                draw3DManager.action(event);
                break;
        }
    }



    public int[] parseArrayList(ArrayList<Point2D> list) {
        int n = list.size() * 2;
        int[] pts = new int[n];

        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            pts[count] = list.get(i).getX();
            count++;
            pts[count] = list.get(i).getY();
            count++;
        }
        return pts;
    }

    /*
    *  public void operationEllipseFill(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getEllipse(x, y, (int) event.getX(), (int) event.getY(), color, bitmap));
                Drawer.fillFigureZatrav(((int) event.getX() - x) / 2 + x, ((int) event.getY() - y) / 2 + y, colorFill, bitmap);
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getEllipse(x, y, (int) event.getX(), (int) event.getY(), color, bitmapMotion);
            }
        }
    }

    public void operationTriangleFill(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, int colorFill, Field field) {
        if (x == -1) {
            x = (int) event.getX();
            y = (int) event.getY();
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                field.getFigures().add(Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), colorFill, bitmap));
                Drawer.fillFigureZatrav(((int) event.getX() - x) / 2 + x, ((int) event.getY() - y) / 2 + y, colorFill, bitmap);
                x = -1;
            } else {
                bitmapMotion.eraseColor(Color.WHITE);
                Drawer.getTriangle(x, y, (int) event.getX(), (int) event.getY(), colorFill, bitmapMotion);
            }
        }
    }

    public void operationErmit(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (point2Ds.size() < 3) {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            } else {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                field.getFigures().add(Drawer.getErmit(parseArrayList(point2Ds).clone(), bitmap, color));
                point2Ds.clear();
            }
        }
    }

    public void operationNURBS(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (point2Ds.size() < 3) {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            } else {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                field.getFigures().add(Drawer.getNURBS(parseArrayList(point2Ds).clone(), bitmap, color));
                point2Ds.clear();
            }
        }
    }

    public void operationBSpline(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event, int color, Field field) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (point2Ds.size() < 3) {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
            } else {
                point2Ds.add(new Point2D((int) event.getX(), (int) event.getY()));
                field.getFigures().add(Drawer.getBSpline(parseArrayList(point2Ds).clone(), bitmap, color));
                point2Ds.clear();
            }
        }
    }

    * */

}
