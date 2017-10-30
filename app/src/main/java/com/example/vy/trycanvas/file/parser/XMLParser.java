package com.example.vy.trycanvas.file.parser;

import com.example.vy.trycanvas.graphics.figures.Figure;

import java.util.ArrayList;

public class XMLParser {

    final String LOG_TAG = "VY_LOGS";

    /*
    * TODO: если будет настроение доделать
    * */

    /*public ArrayList<Figure> parse(ArrayList<String> list) {
        ArrayList<Figure> figures = new ArrayList<>();

        Drawer drawer = Drawer.getInstance();

        for(String line: list){
            String [] masLine = line.split(" ");

            switch (masLine[0]){
                case "rect":
                    figures.add(drawer.getRectangle(Integer.valueOf(masLine[1]),
                            Integer.valueOf(masLine[3]),Integer.valueOf(masLine[2]),Integer.valueOf(masLine[4]),hex2decimal(masLine[5])));
                    break;
                case "line":
                    figures.add(drawer.getLine(Integer.valueOf(masLine[1]),Integer.valueOf(masLine[2]),
                            Integer.valueOf(masLine[3]),Integer.valueOf(masLine[4]),hex2decimal(masLine[5])));
                    break;
                case "round":
                    figures.add(drawer.getRound(Integer.valueOf(masLine[1]),Integer.valueOf(masLine[2]),
                            Integer.valueOf(masLine[3]),hex2decimal(masLine[4]),1));
                    break;
            }
        }

        return figures;
    }*/


    private static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

}
