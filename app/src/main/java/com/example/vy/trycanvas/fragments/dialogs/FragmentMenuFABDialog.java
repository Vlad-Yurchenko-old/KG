package com.example.vy.trycanvas.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.R;
import com.skydoves.colorpickerview.ColorPickerView;

public class FragmentMenuFABDialog extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View linearlayout = inflater.inflate(R.layout.change_pixel, null);

        builder.setView(linearlayout);

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
        builder.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
