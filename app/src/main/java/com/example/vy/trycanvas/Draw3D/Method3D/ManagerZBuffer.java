package com.example.vy.trycanvas.Draw3D.Method3D;

import android.graphics.Bitmap;

public class ManagerZBuffer{
    protected int[][] zBuf;
    int w, h;

    public ManagerZBuffer(int width, int height) {
        this.w = width;
        this.h = height;
        zBuf = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                zBuf[i][j] = Integer.MIN_VALUE;
            }
        }
        copyBuffer(w,h);
    }

    public void clearBufer() {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                zBuf[i][j] = Integer.MIN_VALUE;
            }
        }
    }

    private void copyBuffer(int width, int height) {
        int[][] result = new int[width][height];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                result[i][j] = zBuf[i][j];
            }
        }

        for (int i = w; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result[i][j] = Integer.MIN_VALUE;
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = h; j < height; j++) {
                result[i][j] = Integer.MIN_VALUE;
            }
        }

        zBuf = result;
        w = width;
        h = height;
    }

    public void setPixel(Bitmap bmp, int x, int y, int z, int color) {
        if (x >= 0 && x < w && y >= 0 && y < h) {
            if (zBuf[x][y] < z) {
                zBuf[x][y] = z;
                bmp.setPixel(x, y, color);
            }
        }
    }
}
