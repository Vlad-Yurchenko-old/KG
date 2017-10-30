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

public class FragmentMenuZoomDialog extends DialogFragment {

    private Controller controller = null;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View linearlayout = inflater.inflate(R.layout.change_zoom, null);

        builder.setView(linearlayout);

        SeekBar zoom = (SeekBar) linearlayout.findViewById(R.id.seekBarZoom);
        zoom.setProgress(controller.getZoomScale());
        zoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    progress++;
                }
                controller.setZoomScale(progress);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
