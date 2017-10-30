package com.example.vy.trycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;

import com.example.vy.trycanvas.Draw3D.Draw3DManager;
import com.example.vy.trycanvas.file.FileReader;
import com.example.vy.trycanvas.file.parser.OBJParser;
import com.example.vy.trycanvas.fractals.Biomorph;
import com.example.vy.trycanvas.fractals.Fern;
import com.example.vy.trycanvas.fractals.Julia;
import com.example.vy.trycanvas.fractals.KochSnowflake;
import com.example.vy.trycanvas.fractals.MandelbrotSet;
import com.example.vy.trycanvas.fractals.Plasma;
import com.example.vy.trycanvas.fractals.SierpinskiCarpet;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.pixels.Point2D;
import com.example.vy.trycanvas.graphics.pixels.PointF;


public class Controller {
    public static Context context;

    public static int height = 1280;
    public static int width = 720;
    public static int colorLine = 0xFF000000;
    public static int colorFill = 0xFF000000;
    public static int pixelSize = 1;
    private int ZoomScale = 1;
    public static int nodesNum = 3;
    public static int nodesNum2 = 3;

    /**
     * Текущая операция
     * -1 - нет операции
     * 0 - карандаш
     * 1 - линия
     * 2 - круг
     * 3 - прямоугольник
     * 4 - кривая Безье
     * 5 - полигон 3
     * 6 - затравка замкнутой фигуры
     * 7 - закрашенный прямоугольник
     * 8 - закрашенный многоугольник (градиент)
     * 9 - эллипс
     * cilinder2 - треугольник
     * 11 - line area
     * 12 - line select
     * 13 - line vy
     * 14 - выбор
     * 15 - полигон N
     * 16 - cохранить
     * 17 - открыть
     * 18 - закрашенный многоугольник (статич.)
     * 19 - перенос
     * 20 - масштабирование
     * 21 - поворот
     * 22 - отражение
     * 23 - обрезание Коен для головы
     * 24 - обрезание Сазерленда-Хогдмана для головы
     * 25 - обрезание Вейлера- для головы
     * 26 - обрезание Коен для головы
     * 27 - обрезание Сазерленда-Хогдмана для головы
     * 28 - обрезание Вейлера-Азертона для головы
     *
     * 30 - проекции
     * 100 - тест
     */
    private int currentOperation = -1;
    private Operation operation;
    private Field field;
    private FragmentManager fragmentManager;
    private Draw3DManager draw3DManager;

    private static Controller instance;

    public Controller(Context context, FragmentManager fragmentManager, Draw3DManager draw3DManager) {
        this.context = context;
        this.draw3DManager = draw3DManager;
        this.fragmentManager = fragmentManager;
        operation = new Operation();
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }

    /*
    * TODO: переделать как-нибудь (вызывается в Field()
    * */
    public void initField(Field field) {
        this.field = field;
    }

    public void clear() {
        field.getFigures().clear();
        operation.operationClear();
        currentOperation = -1;
        field.createNewBitmap();
        field.getBitmapMotion().eraseColor(Color.WHITE);
        field.invalidate();
    }

    public int getZoomScale() {
        return ZoomScale;
    }

    public void setZoomScale(int zoomScale) {
        ZoomScale = zoomScale;
        int w = field.getBitmap().getWidth();
        int h = field.getBitmap().getHeight();

        field.setScaleY(ZoomScale);
        field.setScaleX(ZoomScale);
    }

    public void setMozaikOperation() {
        Drawer.getMozaik(field.getBitmap(), pixelSize);
    }

    public void setPenOperation() {
        currentOperation = 0;
        operation.operationClear();
    }

    public void setLineOperation() {
        currentOperation = 1;
        operation.operationClear();
    }

    public void setRoundOperation() {
        currentOperation = 2;
        operation.operationClear();
    }

    public void setRectangleOperation() {
        currentOperation = 3;
        operation.operationClear();
    }

    public void setBezierOperation() {
        currentOperation = 4;
        operation.operationClear();
    }

    public void setPolygon3Operation() {
        currentOperation = 5;
        operation.operationClear();
    }

    public void setPolygonNOperation() {
        currentOperation = 15;
        operation.operationClear();
    }

    public void setZatravOperation() {
        currentOperation = 6;
        operation.operationClear();
    }

    public void setFillRectOperation() {
        currentOperation = 7;
        operation.operationClear();
    }

    public void setFillPolygonNGradOperation() {
        currentOperation = 8;
        operation.operationClear();
    }

    public void setEllipseOperation() {
        currentOperation = 9;
        operation.operationClear();
    }

    public void setTriangle() {
        currentOperation = 10;
        operation.operationClear();
    }

    public void setLineAreaOperation() {
        currentOperation = 11;
        operation.operationClear();
    }

    public void setLineSelectOperation() {
        currentOperation = 12;
        operation.operationClear();
    }

    public void setLineVyOperation() {
        currentOperation = 13;
        operation.operationClear();
    }


    public void setSelectOperation() {
        currentOperation = 14;
        operation.operationClear();
    }

    public void setTransferOperation() {
        currentOperation = 19;
    }

    public void setScalingOperation() {
        currentOperation = 20;
    }

    public void setTurningOperation() {
        currentOperation = 21;
    }

    public void setReflectOperation() {
        currentOperation = 22;
    }



    public void setTrimKoenOperation() {
        currentOperation = 23;
    }

    public void setTrimSHOperation() {
        currentOperation = 24;
    }

    public void setTrimVAOperation() {
        currentOperation = 25;
    }

    public void setTrimKoenForHeadOperation() {
        currentOperation = 26;
    }

    public void setTrimSHForHeadOperation() {
        currentOperation = 27;
    }

    public void setTrimVAForHeadOperation() {
        currentOperation = 28;
    }



    public void setProjectionOperation() {
        currentOperation = 30;
    }




    public void setTESTOperation() {
        currentOperation = 100;
    }


    public void fractalFernOperation() {
        new Fern().draw(field.getBitmap(), colorLine);
    }

    public void fractalSnowflakeOperation() {
        new KochSnowflake().drawKochSnowflake(field.getBitmap(), colorLine, new Point2D(field.getBitmap().getWidth() / 2, field.getBitmap().getHeight() / 2),
                field.getBitmap().getWidth() / 3, 2, 4);
    }

    public void fractalMandelborOperation() {
        new MandelbrotSet(10).draw(field.getBitmap(), colorLine, new PointF(100, 100));
    }

    public void fractalSierpinskiOperation() {
        new SierpinskiCarpet().drawCarpet(field.getBitmap(), colorLine, 5, new PointF(100, 100), new PointF(800, 800));
    }

    public void fractalPlasmaOperation() {
        new Plasma().draw(field.getBitmap());
    }

    public void fractalJuliaOperation() {
        new Julia().draw(field.getBitmap());
    }

    public void fractalBiomorphOperation() {
        new Biomorph().draw(field.getBitmap());
    }


    public void setSave() {
        currentOperation = 16;
        operation.operationClear();
    }

    public void setOpen() {
        currentOperation = 17;
        operation.operationClear();
    }

    public void setFillPolygonNStaticOperation() {
        currentOperation = 18;
        operation.operationClear();
    }


    public void modelHeadOperation() {
        field.getFigures().add(new OBJParser().parse(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                field.getBitmap().getWidth() / 2,
                field.getBitmap().getHeight() / 2,
                field.getBitmap()));
    }

    public void modelHeadStaticOperation() {
        new OBJParser().parseStaticFillStaticColor(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                field.getBitmap().getWidth() / 2,
                field.getBitmap().getHeight() / 2,
                field.getBitmap(), colorFill);
    }

    public void modelHeadRandomOperation() {
        new OBJParser().parseStaticFillRandomColor(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                field.getBitmap().getWidth() / 2,
                field.getBitmap().getHeight() / 2,
                field.getBitmap());
    }





    /**
     * Смотрим текущую операцию и передаем работу соотвутствующему методу
     */
    public void operate(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event) {
        switch (currentOperation) {
            case 0:
                operation.operationPen(event, colorLine, pixelSize, bitmap);
                break;
            case 1:
                operation.operationLine(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 2:
                operation.operationRound(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 3:
                operation.operationRect(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 4:
                operation.operationBezier(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 5:
                operation.operationPolygon3(bitmap, bitmapMotion, event, colorLine, colorFill, field);
                break;
            case 15:
                operation.operationPolygon(bitmap, bitmapMotion, event, nodesNum, colorLine, null);
                break;
            case 6:
                operation.operationZatrav(bitmap, event, colorFill);
                break;
            case 7:
                operation.operationFillRect(bitmap, bitmapMotion, event, colorLine, colorFill, field);
                break;
            case 8:
                operation.operationFillPolygonNGrad(bitmap, bitmapMotion, event, colorLine, colorFill, field, nodesNum);
                break;
            case 9:
                operation.operationEllipse(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 10:
                operation.operationTriangle(bitmap, bitmapMotion, event, colorLine, field);
                break;


            /*
            * Сглаживание
            * */
            case 11:
                operation.operationLineArea(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 12:
                operation.operationLineSelect(bitmap, bitmapMotion, event, colorLine, field);

                break;
            case 13:
                operation.operationLineVy(bitmap, bitmapMotion, event, colorLine, field);
                break;


            /*
            * Трансформирование
            * */
            case 14:
                operation.operationSelect(event, field, this, fragmentManager);
                break;
            case 19:
                operation.operationTransfer(bitmap, bitmapMotion, event, field);
                break;
            case 20:
                operation.operationScaling(bitmap, event, field);
                break;
            case 21:
                operation.operationTurning(bitmap, event);
                break;
            case 22:
                operation.operationReflect(bitmap, bitmapMotion, event);
                break;


            /*
            * Отсечение
            * */
            case 23:
                operation.operationTrimKoen(bitmap, bitmapMotion, event, field);
                break;
            case 24:
                operation.operationTrimSH(bitmap, bitmapMotion, event, field, nodesNum, colorLine);
                break;
            case 25:
                operation.operationTrimVA(bitmap, bitmapMotion, event, field, nodesNum2, nodesNum, colorLine);
                break;
            case 26:
                operation.operationTrimKoenHead(bitmap, bitmapMotion, event, field,context);
                break;
            case 27:
                operation.operationTrimSHHead(bitmap, bitmapMotion, event, field, nodesNum, colorLine,context);
                break;
            case 28:
                operation.operationTrimVAHead(bitmap, bitmapMotion, event, field, nodesNum2, nodesNum,context);
                break;

            /*
            * Работа с файлами
            * */
            case 16:
                operation.operationSave(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 17:
                operation.operationOpen(field);
                break;
            case 18:
                operation.operationFillPolygonNStatic(bitmap, bitmapMotion, event, colorLine, colorFill, field, nodesNum);
                break;



            case 30:
                operation.operationProjection(draw3DManager,bitmap,bitmapMotion,event,colorLine,colorFill,field,context);
                break;

            case 100:
                operation.operationTEST(bitmap,bitmapMotion,event,colorLine,colorFill,field,context);
                break;
        }
    }


}

