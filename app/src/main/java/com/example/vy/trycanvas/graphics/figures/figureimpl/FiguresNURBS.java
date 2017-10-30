package com.example.vy.trycanvas.graphics.figures.figureimpl;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.figures.Figure;

/**
 * Created by HOME on 17.04.2017.
 */

public class FiguresNURBS extends Figure {
    int _rank;
    int _n;
    int[] _t;
    int[][] _N;

    public FiguresNURBS(int rank) {
        _rank = rank;
    }

    public Figure draw(int[] points, Bitmap bmp) {
        _n = points.length / 2;
        _t = new int[_n + _rank + 1];

        int k = 0;
        while (k <= _rank) {
            _t[k++] = 0;
        }
        while (k < _n) {
            _t[k] = k - _rank + 1;
            k++;
        }
        int temp = _n - _rank + 2;
        while (k <= _n + _rank) {
            _t[k] = temp;
            k++;
        }
        float step = .001F;
        float t = .0F;
        float resX, resY;
        while (t <= temp) {
            resX = 0;
            resY = 0;
            for (int j = 0, i = 0; j < _n; j++, i += 2) {
                resX += points[i] * N(j, _rank, t);
                resY += points[i + 1] * N(j, _rank, t);
            }
            try {
                bmp.setPixel((int) resX, (int) resY, color);
            } catch (Exception e) {
            }
            t += step;
        }
        return this;
    }


    private float N(int i, int k, float t) {
        if (k == 0) {
            if (t >= _t[i] && t < _t[i + 1]) {
                return 1;
            } else {
                return 0;
            }
        } else {
            float ch1 = (t - _t[i]) * N(i, k - 1, t);
            int znam1 = (_t[i + k] - _t[i]);

            if (znam1 == 0) {
                ch1 = 0;
            } else {
                ch1 /= znam1;
            }

            float ch2 = (_t[i + k + 1] - t) * N(i + 1, k - 1, t);
            int znam2 = (_t[i + k + 1] - _t[i + 1]);

            if (znam2 == 0) {
                ch2 = 0;
            } else {
                ch2 /= znam2;
            }

            return ch1 + ch2;
        }
    }
}
