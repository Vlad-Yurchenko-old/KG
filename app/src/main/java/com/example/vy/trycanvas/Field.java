package com.example.vy.trycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.example.vy.trycanvas.graphics.figures.Figure;
import java.util.ArrayList;

/**
* Инициализируется в начале работы программы
* */
public class Field extends View {

    private static Field instance;

    private ArrayList<Figure> figures;
    private Bitmap bitmap;
    private Bitmap bitmapMotion;
    public static int width, height;
    private Paint paint;
    private Controller controller;

    public Field(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;

        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        bitmapMotion = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        paint = new Paint();
        figures = new ArrayList<>();

        controller = Controller.getInstance();
        bitmapMotion.eraseColor(Color.WHITE);

        instance = this;
        controller.initField(instance);

    }

    public static Field getInstance(){
        return instance;
    }


    public Bitmap getBitmapMotion() {
        return bitmapMotion;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap value) {
        this.bitmap = value;
        invalidate();
    }
    public void createNewBitmap(){
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.save();
        canvas.drawBitmap(bitmapMotion,0,0,paint);
        canvas.drawBitmap(bitmap,0,0,paint);
        //canvas.restore();
    }

    @Override
    public void setScaleY(float scaleY) {
        super.setScaleY(scaleY);
    }

    @Override
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.operate(bitmap,bitmapMotion,event);
        invalidate();
        return true;
    }

}