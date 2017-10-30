package com.example.vy.trycanvas.file;
import android.graphics.Bitmap;
import android.graphics.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ReadBMP {
    BufferedInputStream _fs;
    int _size = 0, _offset = 0, _width = 0, _height = 0;

    public ReadBMP(File file) throws FileNotFoundException {
        _fs = new BufferedInputStream(new FileInputStream(file));
    }

    public Bitmap read() throws IOException {
        Bitmap bmp;

        //считывание типа файла
        if (readBytes(2) != 0x4D42);

        //считывание размера
        _size = readBytes(4);
        if (_size == -1);

        //пропуск пустых байтов
        if (readBytes(4) == -1) return null;

        //считывание адресса начала массива цветов
        if (readBytes(4) == -1) return null;

        //считывание размера структуры
        if (readBytes(4) == -1) return null;

        //считывание ширины
        _width = readBytes(4);
        if (_width == -1) return null;

        //считывание высоты
        _height = readBytes(4);
        if (_height == -1) return null;

        int spacerSize = 0;
        if (((_width * 3) % 4) > 0) {
            spacerSize = 4 - (_width * 3) % 4;
        }

        int lenght = _width * _height * 3 + _height * spacerSize;

        if (_size != lenght + 54);


        //считывание количества плоскостей
        if (readBytes(2) != 1) return null;

        //считывание количества бит на пиксель
        if (readBytes(2) != 24);

        //считывание метода сжатия
        if (readBytes(4) != 0);

        //считывание длинны растрового массива
        if (readBytes(4) != 0);

        //считывание горизонтального разрешения
        if (readBytes(4) == -1) return null;

        //считывание вертикального разрешения
        if (readBytes(4) == -1) return null;

        //считывание количество цветов изображения
        if (readBytes(4) == -1) return null;

        //считывание количества основных цветов изображения
        if (readBytes(4) == -1) return null;

        bmp = Bitmap.createBitmap(_width, _height, Bitmap.Config.ARGB_8888);

        for (int i = _height - 1; i >= 0; i--) {
            for (int j = 0; j < _width; j++) {
                int r, g, b;

                b = readBytes(1);


                g = readBytes(1);


                r = readBytes(1);


                bmp.setPixel(j, i, Color.rgb(r, g, b));
            }
            //выравнивание
            if (readBytes(spacerSize) == -1);

        }

        _fs.close();
        return bmp;
    }

    private int readBytes(int n) throws IOException {
        int result = 0;
        int offset = 0;
        for (int i = 0; i < n; i++) {
            int tByte = _fs.read();
            if (tByte == -1) {
                return -1;
            }
            tByte <<= offset;
            result |= tByte;
            offset += 8;
        }
        return result;
    }
}
