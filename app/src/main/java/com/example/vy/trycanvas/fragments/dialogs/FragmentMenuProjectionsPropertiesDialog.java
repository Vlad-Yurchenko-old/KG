package com.example.vy.trycanvas.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.vy.trycanvas.Controller;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.vy.trycanvas.Draw3D.Draw3DManager;
import com.example.vy.trycanvas.Draw3D.Method3D.DrawMethod3D;
import com.example.vy.trycanvas.Draw3D.Method3D.DrawMethodGraph;
import com.example.vy.trycanvas.Draw3D.Method3D.DrawMethodObj3D;
import com.example.vy.trycanvas.Draw3D.Method3D.ManagerZBuffer;
import com.example.vy.trycanvas.Draw3D.Projection3D.Projection3DCenter;
import com.example.vy.trycanvas.Draw3D.Projection3D.Projection3DObliqueAxonometric;
import com.example.vy.trycanvas.Draw3D.Projection3D.Projection3DOrtogonal;
import com.example.vy.trycanvas.Draw3D.Projection3D.Projection3DRectAxonometric;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DMove;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DReflect.Transform3DReflectOxy;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DReflect.Transform3DReflectOxz;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DReflect.Transform3DReflectOyz;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DRotate.Transform3DRotateOx;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DRotate.Transform3DRotateOy;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DRotate.Transform3DRotateOz;
import com.example.vy.trycanvas.Draw3D.Transform3D.Transform3DScale;
import com.example.vy.trycanvas.Field;
import com.example.vy.trycanvas.R;
import com.example.vy.trycanvas.file.FileReader;
import com.example.vy.trycanvas.file.parser.OBJParser;
import com.example.vy.trycanvas.graphics.figures.figureimpl.Triangle;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.ArrayList;

public class FragmentMenuProjectionsPropertiesDialog  extends DialogFragment {

    Controller controller;
    Draw3DManager draw3DManager;
    Context context;

    boolean flag; // для 2х графиков

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setDraw3DManager(Draw3DManager draw3DManager) {
        this.draw3DManager = draw3DManager;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View linearlayout = inflater.inflate(R.layout.choose_3d_propeties, null);

        Spinner spinner3DFigure = (Spinner) linearlayout.findViewById(R.id.spinner3DFigure);
        Spinner spinner3DMethod = (Spinner) linearlayout.findViewById(R.id.spinner3DMethod);
        Spinner spinner3DAction = (Spinner) linearlayout.findViewById(R.id.spinner3DAction);
        Spinner spinner3DProjection = (Spinner) linearlayout.findViewById(R.id.spinner3DProjection);

        spinner3DFigure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.figureList);
                OBJParser objParser = new OBJParser();
                ArrayList<Triangle> polygon3s = null;
                switch (choose[position]){
                    case "Негретянский мальчик":
                        polygon3s = objParser.parseTriangle3D(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                                Field.width/5,
                                Field.height/5);
                        draw3DManager.setFigure3d(polygon3s);
                        break;
                    case "Додекаэдр":
                        polygon3s = objParser.parseTriangle3D(FileReader.readFile(context.getResources().openRawResource(R.raw.dodecahedron)),
                                Field.width/50,
                                Field.width/50);
                        draw3DManager.setFigure3d(polygon3s);
                        break;
                    case "Цилиндр":
                        polygon3s = objParser.parseTriangle3D(FileReader.readFile(context.getResources().openRawResource(R.raw.cilinder2)),
                                Field.width/5,
                                Field.height/5);
                        draw3DManager.setFigure3d(polygon3s);
                        break;
                    case "График1":
                        flag = true;
                        draw3DManager.setFigure3dPoints(onSet(200,200));
                        break;
                    case "График2":
                        flag = false;
                        draw3DManager.setFigure3dPoints(onSet(200,200));
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner3DMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.methodList);
                switch (choose[position]){
                    case "Z - буфер":
                        draw3DManager.setMethod3D(new DrawMethodObj3D(new ManagerZBuffer(Controller.width, Controller.height)));
                        break;
                    case "Плавающий горизонт":
                        draw3DManager.setMethod3D(new DrawMethodGraph());
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner3DAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.actionList);
                switch (choose[position]){
                    case "Перемещение":
                        draw3DManager.setTransform3D(new Transform3DMove());
                        break;
                    case "Приближение / Удаление":
                        draw3DManager.setTransform3D(new Transform3DScale());
                        break;
                    case "Отражение (Oxy)":
                        draw3DManager.setTransform3D(new Transform3DReflectOxy());
                        break;
                    case "Отражение (Oxz)":
                        draw3DManager.setTransform3D(new Transform3DReflectOxz());
                        break;
                    case "Отражение (Oyz)":
                        draw3DManager.setTransform3D(new Transform3DReflectOyz());
                        break;
                    case "Вражение (Ox)":
                        draw3DManager.setTransform3D(new Transform3DRotateOx());
                        break;
                    case "Вражение (Oy)":
                        draw3DManager.setTransform3D(new Transform3DRotateOy());
                        break;
                    case "Вражение (Oz)":
                        draw3DManager.setTransform3D(new Transform3DRotateOz());
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner3DProjection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.projectionList);
                switch (choose[position]){
                    case "Ортогональная проекция":
                        draw3DManager.setProjection3D(new Projection3DOrtogonal());
                        break;
                    case "Прямоугольная аксонометрическая проекция":
                        draw3DManager.setProjection3D(new Projection3DRectAxonometric());
                        break;
                    case "Косоугольная аксонометрическая проекция":
                        draw3DManager.setProjection3D(new Projection3DObliqueAxonometric());
                        break;
                    case "Центральная одноточечная проекция":
                        draw3DManager.setProjection3D(new Projection3DCenter());
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        ((CheckBox) linearlayout.findViewById(R.id.checkBoxChooseAngle)).setChecked(Draw3DManager.changeAngle);

        ((CheckBox) linearlayout.findViewById(R.id.checkBoxChooseAngle)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Draw3DManager.changeAngle = isChecked;
            }
        });

        builder.setView(linearlayout);
        final AlertDialog alertDialog = builder.create();

        ((Button) linearlayout.findViewById(R.id.choose3dPropertiesBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }



    /*
    * gauno
    * */
    int rw, lw, uh, dh, lz, rz;
    public ArrayList<Point3D> onSet(int width, int height) {
        rw = width / 2;
        lw = -rw;
        uh = height / 2;
        dh = -uh;
        lz = -height;
        rz = height;
        int step = 1;
        ArrayList<Point3D> list = new ArrayList<>();
        for (int k = lz; k < rz; k++) {
            for (int i = lw; i < rw; i += step) {
                for (int j = dh; j < uh; j += step) {
                    if(flag){
                        if (func(i, j, k)) {
                            Point3D p = new Point3D(i, j, k);
                            list.add(p);
                        }
                    } else {
                        if (func2(i, j, k)) {
                            Point3D p = new Point3D(i, j, k);
                            list.add(p);
                        }
                    }
                }
            }
        }
        return list;
    }
    boolean func(float x, float y, float z) {
        return Math.abs(x * x / 50 + y * y / 50 - z) <= 5 ;
    }
    boolean func2(float x, float y, float z) {
        return Math.abs(x * x * x / 50 + y * y * y / 50 - z)<= 5 ;
    }

}
