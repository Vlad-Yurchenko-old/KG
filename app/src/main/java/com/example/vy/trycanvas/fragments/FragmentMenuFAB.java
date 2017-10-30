package com.example.vy.trycanvas.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.R;
import com.skydoves.colorpickerview.ColorPickerView;

public class FragmentMenuFAB extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View linearlayout = inflater.inflate(R.layout.change_pixel, null);
        SeekBar size = (SeekBar) linearlayout.findViewById(R.id.seekBar);
        size.setProgress(Controller.pixelSize);
        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    progress++;
                }
                Controller.pixelSize = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ColorPickerView colorPickerLine = (ColorPickerView) linearlayout.findViewById(R.id.colorPickerLine);
        ColorPickerView colorPickerFill = (ColorPickerView) linearlayout.findViewById(R.id.colorPickerFill);
        final View colorLineRect = linearlayout.findViewById(R.id.colorLineRect);
        final View colorFillRect = linearlayout.findViewById(R.id.colorFillRect);
        colorLineRect.setBackgroundColor(Controller.colorLine);
        colorFillRect.setBackgroundColor(Controller.colorFill);
        colorPickerLine.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                if(color!=-65539 && color!=-65538){
                    Controller.colorLine = color;
                    colorLineRect.setBackgroundColor(color);
                }
            }
        });
        colorPickerFill.setColorListener(new ColorPickerView.ColorListener() {
            @Override
            public void onColorSelected(int color) {
                if(color!=-65539 && color!=-65538) {
                    Controller.colorFill = color;
                    colorFillRect.setBackgroundColor(color);
                }
            }
        });
        return linearlayout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

}
