package com.example.vy.trycanvas.clipping;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.example.vy.trycanvas.graphics.Drawer;

public class TrimMethodKoen {

    public static void draw(Bitmap bmp, int[] points) {
        Drawer.getRectangle(points[0],points[1],points[2],points[3], Color.RED,bmp);

        /*
        Log.d("VY_LOGS","=======================");
        for(int i = 0; i <= points.length; i++){
            Log.d("VY_LOGS", String.valueOf(points[i]));
        }
        Log.d("VY_LOGS","=======================");*/

        int[] temp = new int[4];
        for (int i = 4; i <= points.length - 4; i += 4) {
            for (int j = 0, k = i; j < 4; j++, k++) {
                temp[j] = points[k];
            }

            int codeA, codeB, code;
            int tempX, tempY;

            codeA = vCode(points, temp[0], temp[1]);
            codeB = vCode(points, temp[2], temp[3]);

            boolean check = true;
            while ((codeA | codeB) != 0) {
                if ((codeA & codeB) != 0) {
                    check = false;
                    break;
                }
                if (codeA != 0) {
                    code = codeA;
                    tempX = temp[0];
                    tempY = temp[1];
                } else {
                    code = codeB;
                    tempX = temp[2];
                    tempY = temp[3];
                }

                if ((code & LEFT) != 0) {
                    tempY += (temp[1] - temp[3]) * (points[0] - tempX) / (temp[0] - temp[2]);
                    tempX = points[0];
                } else if ((code & RIGHT) != 0) {
                    tempY += (temp[1] - temp[3]) * (points[2] - tempX) / (temp[0] - temp[2]);
                    tempX = points[2];
                }// если c ниже r, то передвигаем c на прямую y = r->y_min если c выше r, то передвигаем c на прямую y = r->y_max
                else if ((code & BOT) != 0) {
                    tempX += (temp[0] - temp[2]) * (points[1] - tempY) / (temp[1] - temp[3]);
                    tempY = points[1];
                } else if ((code & TOP) != 0) {
                    tempX += (temp[0] - temp[2]) * (points[3] - tempY) / (temp[1] - temp[3]);
                    tempY = points[3];
                }

                    /* обновляем код */
                if (code == codeA) {
                    temp[0] = tempX;
                    temp[1] = tempY;
                    codeA = vCode(points, tempX, tempY);
                } else {
                    temp[2] = tempX;
                    temp[3] = tempY;
                    codeB = vCode(points, tempX, tempY);
                }
            }

            if (check){
                Log.d("VY_LOGS", String.valueOf("entered : " + temp[0] + " " + temp[1] + " - " + temp[2] + " " + temp[3]));
                Drawer.getLine(temp[0],temp[1],temp[2], temp[3],Color.BLACK,bmp);
            }
        }
    }

    private static final int LEFT = 1; /* двоичное 0001 */
    private static final int RIGHT = 2; /* двоичное 0010 */
    private static final int BOT = 4; /* двоичное 0100 */
    private static final int TOP = 8;  /* двоичное 1000 */

    private static int vCode(int points[], int x, int y) {
        int result = 0;

        result += x < points[0] ? LEFT : 0;
        result += x > points[2] ? RIGHT : 0;
        result += y < points[1] ? BOT: 0;
        result += y > points[3] ? TOP : 0;

        return result;
    }
}
