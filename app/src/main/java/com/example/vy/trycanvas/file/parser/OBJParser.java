package com.example.vy.trycanvas.file.parser;


import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.figures.figureimpl.FigurePolygon3;
import com.example.vy.trycanvas.graphics.figures.figureimpl.ModelHead;
import com.example.vy.trycanvas.graphics.figures.figureimpl.Triangle;
import com.example.vy.trycanvas.graphics.pixels.Point2D;
import com.example.vy.trycanvas.graphics.pixels.Point3D;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OBJParser {


    final String _patPoint = "v\\s+([^\\s]+)*\\s+([^\\s]+)*\\s+([^\\s]+)*";
    final Pattern _point = Pattern.compile(_patPoint);
    final String _patTreangl = "f\\s+([^/]+)/?([^/]+)?/?([^\\s]+)?\\s+([^/]+)/?([^/]+)?/?([^\\s]+)?\\s+([^/]+)/?([^/]+)?/?([^\\s]+)?";
    final Pattern _treangl = Pattern.compile(_patTreangl);


    public ArrayList<Triangle> parseTriangle3D(ArrayList<String> list, int width, int height) {

        ArrayList<Triangle> triangles = new ArrayList<>();
        ArrayList<Point3D> points = new ArrayList<>();

        // читаем содержимое
        for (String str : list){
            Matcher temp = _point.matcher(str);
            if (temp.find()) {
                Point3D point = new Point3D();
                point.x = Float.valueOf(temp.group(1))*width+width;
                point.y = Float.valueOf(temp.group(2))*height+height;
                point.z = Float.valueOf(temp.group(3))*width+width;
                points.add(point);
                //Log.d(LOG_TAG, temp.group(1) + " " + temp.group(2));
            }
            temp = _treangl.matcher(str);
            if (temp.find()) {
                int p[] = new int[3];
                p[0] = Integer.valueOf(temp.group(1)) - 1;
                p[1] = Integer.valueOf(temp.group(4)) - 1;
                p[2] = Integer.valueOf(temp.group(7)) - 1;

                Point3D p1[] = new Point3D[3];
                p1[0] = points.get(p[0]).clone();
                p1[1] = points.get(p[1]).clone();
                p1[2] = points.get(p[2]).clone();
                Triangle tr = new Triangle(p1[0],p1[1],p1[2]);
                triangles.add(tr);
            }

        }
        return triangles;
    }

    public ArrayList<Point2D> parsePoints(ArrayList<String> list, int width, int height) {
        ArrayList<Point2D> points = new ArrayList<>();
        for(String str : list) {
            if (str != null && str.compareTo("") != 0) {
                if (str.charAt(0) == 'f') {
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0]) - 1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0]) - 1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0]) - 1;
                    int xA = (int) ((Double.valueOf(list.get(lineA).split(" ")[1]) + 1.) * width / 2);
                    int yA = (int) ((Double.valueOf(list.get(lineA).split(" ")[2]) * -1 + 1.) * height / 2);
                    int xB = (int) ((Double.valueOf(list.get(lineB).split(" ")[1]) + 1.) * width / 2);
                    int yB = (int) ((Double.valueOf(list.get(lineB).split(" ")[2]) * -1 + 1.) * height / 2);
                    int xC = (int) ((Double.valueOf(list.get(lineC).split(" ")[1]) + 1.) * width / 2);
                    int yC = (int) ((Double.valueOf(list.get(lineC).split(" ")[2]) * -1 + 1.) * height / 2);
                    points.add(new Point2D(xA, yA));
                    points.add(new Point2D(xB, yB));
                    points.add(new Point2D(xC, yC));
                }
            }
        }
        return points;
    }

    public ArrayList<FigurePolygon3> parseTriangles(ArrayList<String> list, int width, int height) {
        ArrayList<FigurePolygon3> polygon3s = new ArrayList<>();
        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;
                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);
                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);
                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);
                    polygon3s.add(new FigurePolygon3(new int[] {xA, yA, xB, yB, xC, yC}));
                }
            }
        }
        return polygon3s;
    }


    public Figure parse(ArrayList<String> list, int width, int height, Bitmap bitmap) {

        int color = Controller.colorLine;

        ModelHead model = new ModelHead();

        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    //Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);


                    //model.getFigure().addAll(Drawer.getLine(xA,yA,xB,yB,color,bitmap).getFigure());
                    //model.getFigure().addAll(Drawer.getLine(xA,yA,xC,yC,color,bitmap).getFigure());
                    //model.getFigure().addAll(Drawer.getLine(xC,yC,xB,yB,color,bitmap).getFigure());

                    model.getFigure().addAll(Drawer.getLine(xA,yA,xB,yB,color,bitmap).getFigure());
                    model.getFigure().addAll(Drawer.getLine(xA,yA,xC,yC,color,bitmap).getFigure());
                    model.getFigure().addAll(Drawer.getLine(xC,yC,xB,yB,color,bitmap).getFigure());

                }
            }
        }

        return model;
    }

    public ArrayList<Figure> parseStaticFillStaticColor(ArrayList<String> list, int width, int height, Bitmap bitmap, int colorFill) {

        int colorLine = Controller.colorLine;

        ArrayList<Figure> figures = new ArrayList<>();

        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    //Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);




                    int [] points = {xA,yA,xB,yB,xC,yC};

                    Drawer.getFillPolygonNStatic(points,bitmap,colorLine,colorFill);
                    figures.add(Drawer.getLine(xA,yA,xB,yB,colorLine,bitmap));
                    figures.add(Drawer.getLine(xA,yA,xC,yC,colorLine,bitmap));
                    figures.add(Drawer.getLine(xC,yC,xB,yB,colorLine,bitmap));
                }
            }
        }

        return figures;
    }

    public ArrayList<Figure> parseStaticFillRandomColor(ArrayList<String> list, int width, int height, Bitmap bitmap) {

        int colorLine = Controller.colorLine;

        ArrayList<Figure> figures = new ArrayList<>();
        Random random = new Random();
        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    //Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);


                    //figures.add(Drawer.getLine(xA,yA,xB,yB,colorLine,bitmap));
                    //figures.add(Drawer.getLine(xA,yA,xC,yC,colorLine,bitmap));
                    //figures.add(Drawer.getLine(xC,yC,xB,yB,colorLine,bitmap));

                    int [] points = {xA,yA,xB,yB,xC,yC};

                    int color = random.nextInt() % Color.rgb(125,125,125) + Color.rgb(125,125,125);
                    Drawer.getFillPolygonNStatic(points,bitmap,colorLine,color);

                }
            }
        }

        return figures;
    }

}
